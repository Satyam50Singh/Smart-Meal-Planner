package com.satya.smartmealplanner.data.model.weeklyMealPlan

data class Week(
    val sunday: MealDay,
    val monday: MealDay,
    val tuesday: MealDay,
    val wednesday: MealDay,
    val thursday: MealDay,
    val friday: MealDay,
    val saturday: MealDay
)