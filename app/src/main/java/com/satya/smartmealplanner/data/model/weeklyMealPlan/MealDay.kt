package com.satya.smartmealplanner.data.model.weeklyMealPlan

data class MealDay(
    val meals: List<Meal>,
    val nutrients: Nutrients
)