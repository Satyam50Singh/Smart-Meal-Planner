package com.satya.smartmealplanner.ui.searchByCuisine

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.recipeByCuisine.Result
import com.satya.smartmealplanner.presentation.navigation.Screen
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.searchByCuisine.components.CuisinesFilterBottomSheet
import com.satya.smartmealplanner.ui.utils.Loader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchByCuisineScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCuisine by remember { mutableStateOf("") }
    var selectedDiet by remember { mutableStateOf("") }

    val recipeByCuisine = viewModel.recipeByCuisineState

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.outline_arrow_back),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = { navController.popBackStack() }
                )
            )

            Text(
                text = "Search By Cuisine",
                fontSize = 22.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = {
                    showBottomSheet = true
                },
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp)
            ) {
                Text(text = "Filter")
            }
        }

        when {
            recipeByCuisine.isLoading -> Loader()

            recipeByCuisine.error != null -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${recipeByCuisine.error}",
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            recipeByCuisine.recipes == null -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { Text("No recipes found") }


            else -> recipeByCuisine.recipes.results.forEach { recipe ->
                RecipeByCuisineCard(recipe, navController)
            }

        }
    }

    if (showBottomSheet) {
        CuisinesFilterBottomSheet(
            onApplyClick = { cuisine, diet ->
                selectedCuisine = cuisine
                selectedDiet = diet
                showBottomSheet = false
                viewModel.getRecipeByCuisine(cuisine, diet)
            },
            onDismiss = {
                showBottomSheet = false
            }
        )
    }
}

@Composable
fun RecipeByCuisineCard(recipe: Result, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        onClick = { navController.navigate(Screen.RecipeDetailById.createRoute(recipe.id.toString())) }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(recipe.image),
                contentDescription = null,
                contentScale = ContentScale.Crop, // <-- important!
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)) {
                Text(
                    recipe.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (recipe.summary.isNotEmpty()) {
                    Text(
                        text = Html.fromHtml(
                            recipe.summary,
                            Html.FROM_HTML_MODE_COMPACT
                        ).toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
}