package com.satya.smartmealplanner.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")

    object FindByIngredient : Screen("findByIngredient")

    object RecipeDetailById : Screen("recipeDetailById/{recipeId}") {
        fun createRoute(recipeId: String) = "recipeDetailById/$recipeId"
    }

    object SearchByCuisines : Screen("searchByCuisines")
}