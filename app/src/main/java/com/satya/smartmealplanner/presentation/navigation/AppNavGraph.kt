package com.satya.smartmealplanner.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.satya.smartmealplanner.ui.home.DashboardScreen
import com.satya.smartmealplanner.ui.home.FindByIngredientScreen

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
            FindByIngredientScreen(navController)
        }
    }
}