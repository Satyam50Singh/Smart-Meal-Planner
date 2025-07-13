package com.satya.smartmealplanner.data.model.recipeByCuisine

data class RecipeByCuisine(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)