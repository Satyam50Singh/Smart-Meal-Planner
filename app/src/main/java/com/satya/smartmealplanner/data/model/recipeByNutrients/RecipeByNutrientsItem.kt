package com.satya.smartmealplanner.data.model.recipeByNutrients

data class RecipeByNutrientsItem(
    val calories: Int,
    val carbs: String,
    val fat: String,
    val id: Int,
    val image: String,
    val imageType: String,
    val protein: String,
    val title: String
)