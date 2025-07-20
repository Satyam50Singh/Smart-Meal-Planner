package com.satya.smartmealplanner.data.model.randomRecipes

data class Equipment(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String,
    val temperature: Temperature
)