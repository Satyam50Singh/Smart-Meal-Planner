package com.satya.smartmealplanner.ui.searchByNutrients.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrientsItem

@Composable
fun RecipeList(navController: NavController, recipeList: List<RecipeByNutrientsItem>) {
    LazyColumn {
        items(recipeList) { recipe ->
            RecipeItem(navController, recipe)
        }
    }
}
