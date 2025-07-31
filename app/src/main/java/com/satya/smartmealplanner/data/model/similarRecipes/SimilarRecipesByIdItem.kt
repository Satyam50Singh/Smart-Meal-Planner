package com.satya.smartmealplanner.data.model.similarRecipes

data class SimilarRecipesByIdItem(
    val id: Int,
    val image: String,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)