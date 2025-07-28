package com.satya.smartmealplanner.ui.findByIngredients

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.recipeDetailsById.components.RecipeCard
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer
import com.satya.smartmealplanner.utils.UIHelpers

@Composable
fun SearchByIngredientScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    var input by remember { mutableStateOf("") }

    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier
        .padding(8.dp)
        .pointerInput(Unit) {
            focusManager.clearFocus()
        }) {

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
                text = "Recipes by Ingredients",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }
        OutlinedTextField(
            value = input,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                input = it
                if (it.isEmpty()) {
                    viewModel.clearRecipes()
                } else if (it.length >= 3) {
                    viewModel.findByIngredients(it)
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),  // replaces containerColor
                unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
            label = { Text("Search by Ingredients . . .") },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.outline_search),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (input.isNotEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.outline_close),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            input = ""
                            viewModel.clearRecipes()
                        }
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.outline_mic),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            UIHelpers.customToast(context, "Coming Soon")
                        }
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> CircularLoader()

            uiState.isError != null -> ErrorContainer(message = "Error: ${uiState.isError}")

            uiState.isSuccess.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            "Search here to find recipes",
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 18.sp
                        )
                    }
                }
            }

            else ->
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    content = {
                        items(uiState.isSuccess) { recipe ->
                            RecipeCard(recipe, navController)
                        }
                    }
                )
        }
    }
}

