package com.satya.smartmealplanner.data.model.recipeByNutrients

data class NutrientRange(
    val carbs: ClosedFloatingPointRange<Float>? = null,
    val protein: ClosedFloatingPointRange<Float>? = null,
    val calories: ClosedFloatingPointRange<Float>? = null,
    val fat: ClosedFloatingPointRange<Float>? = null
)
