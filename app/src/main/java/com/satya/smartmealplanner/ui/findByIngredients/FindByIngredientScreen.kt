package com.satya.smartmealplanner.ui.findByIngredients

import android.widget.Toast
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.findByIngredients.components.RecipeCard
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer

@Composable
fun FindByIngredientScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    var input by remember { mutableStateOf("") }

    val context = LocalContext.current

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
                }
            },
            label = { Text("Enter ingredients (comma separated)") },
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.outline_search),
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        onClick = {
                            if (input.isNotEmpty()) {
                                viewModel.findByIngredients(input)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter ingredients",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ))
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
                        uiState.isSuccess?.let { recipes ->
                            items(recipes) { recipe ->
                                RecipeCard(recipe, navController)
                            }
                        }
                    }
                )
        }
    }
}

