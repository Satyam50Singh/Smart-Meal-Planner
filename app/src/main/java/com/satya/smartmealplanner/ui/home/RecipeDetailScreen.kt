package com.satya.smartmealplanner.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.satya.smartmealplanner.presentation.search.RecipeViewModel

@Composable
fun RecipeDetailScreen(recipeId: String?, viewModel: RecipeViewModel = hiltViewModel()) {
    Text(text = "Recipe Detail Screen")

    LaunchedEffect(Unit) {
        viewModel.getRecipeById(recipeId)
    }
}