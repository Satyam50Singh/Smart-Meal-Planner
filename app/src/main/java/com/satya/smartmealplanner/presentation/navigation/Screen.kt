package com.satya.smartmealplanner.presentation.navigation

sealed class Screen(val route:String) {
    object Dashboard : Screen("dashboard")
    object FindByIngredient : Screen("findByIngredient")
}