package com.satya.smartmealplanner.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.satya.smartmealplanner.ui.favorites.Favorites
import com.satya.smartmealplanner.ui.home.DashboardScreen
import com.satya.smartmealplanner.ui.findByIngredients.SearchByIngredientScreen
import com.satya.smartmealplanner.ui.recipeDetailsById.RecipeDetailScreen
import com.satya.smartmealplanner.ui.searchByCuisine.SearchByCuisineScreen
import com.satya.smartmealplanner.ui.searchByNutrients.SearchByNutrientsScreen
import com.satya.smartmealplanner.ui.weeklyMealPlanner.WeeklyMealPlannerScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    destination: String = Screen.Dashboard.route,
) {
    NavHost(
        navController = navController,
        startDestination = destination,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.FindByIngredient.route) {
            SearchByIngredientScreen(navController)
        }

        composable(Screen.RecipeDetailById.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
            RecipeDetailScreen(recipeId?.toIntOrNull(), navController)
        }

        composable(Screen.SearchByCuisines.route) {
            SearchByCuisineScreen(navController)
        }

        composable(Screen.SearchByNutrients.route) {
            SearchByNutrientsScreen(navController)
        }

        composable(Screen.WeeklyMealPlanner.route) {
            WeeklyMealPlannerScreen(navController)
        }

        composable(Screen.Favorites.route) {
            Favorites(navController)
        }
    }
}