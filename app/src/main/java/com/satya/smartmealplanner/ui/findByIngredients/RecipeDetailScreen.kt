package com.satya.smartmealplanner.ui.findByIngredients

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.findByIngredients.components.RecipeDetailCard
import com.satya.smartmealplanner.ui.utils.Loader

@Composable
fun RecipeDetailScreen(
    recipeId: Int?,
    navController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val selectedRecipeState = viewModel.selectedRecipeState

    LaunchedEffect(Unit) {
        recipeId?.let { viewModel.getRecipeById(it) }
    }
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
                text = selectedRecipeState.recipe?.title?: "Details",
                fontSize = 22.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        when {
            selectedRecipeState.isLoading -> Loader()

            selectedRecipeState.error != null -> {
                Text("Error: ${selectedRecipeState.error}")
            }

            selectedRecipeState.recipe == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("No details found, Please try another recipe") }
            }

            else -> {
                val recipe = selectedRecipeState.recipe
                RecipeDetailCard(recipe)
            }
        }
    }
}