package com.satya.smartmealplanner.presentation.navigation

import com.satya.smartmealplanner.R

sealed class Screen(val route: String, val title: String = "", val icon: Int = 0) {
    object Dashboard : Screen("dashboard", "Home", R.drawable.outline_home)

    object FindByIngredient : Screen("findByIngredient")

    object RecipeDetailById : Screen("recipeDetailById/{recipeId}") {
        fun createRoute(recipeId: String) = "recipeDetailById/$recipeId"
    }

    object SearchByCuisines : Screen("searchByCuisines")

    object SearchByNutrients : Screen("searchByNutrients")

    object WeeklyMealPlanner : Screen("weeklyMealPlanner")
    object Favorites : Screen("favorites", "Favorites", R.drawable.outline_favorite)
}