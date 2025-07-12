package com.satya.smartmealplanner.domain.repository

import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse

interface RecipeRepository {

    suspend fun findByIngredients(
        ingredients: String,
        number: Int,
        apiKey: String
    ): FindByIngredientsResponse

}