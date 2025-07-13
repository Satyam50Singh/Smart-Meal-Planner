package com.satya.smartmealplanner.domain.repository

import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails

interface RecipeRepository {

    suspend fun findByIngredients(
        ingredients: String,
        number: Int,
        apiKey: String
    ): FindByIngredientsResponse

    suspend fun getRecipeDetailsById(recipeId: Int): SelectedRecipeDetails

    suspend fun getRecipeByCuisine(cuisine: String, diet: String): RecipeByCuisine
}