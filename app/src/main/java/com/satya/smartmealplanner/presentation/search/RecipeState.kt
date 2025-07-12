package com.satya.smartmealplanner.presentation.search

import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse

data class RecipeState(
    val isLoading: Boolean = false,
    val recipes: FindByIngredientsResponse = FindByIngredientsResponse(),
    val error: String? = null
)