package com.satya.smartmealplanner.ui.searchByNutrients.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients

@Composable
fun RecipeList(navController: NavController, recipeList: RecipeByNutrients) {
    LazyColumn {
        items(recipeList) { recipe ->
            RecipeItem(navController, recipe)
        }
    }
}
