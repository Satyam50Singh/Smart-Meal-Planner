package com.satya.smartmealplanner.domain.usecase

import com.satya.smartmealplanner.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(
        ingredients: String,
        number: Int,
        apiKey: String
    ) = repository.findByIngredients(ingredients, number, apiKey)


    suspend fun getRecipeDetailsById(recipeId: Int) = repository.getRecipeDetailsById(recipeId)

    suspend fun getRecipeByCuisine(cuisine: String, diet: String) = repository.getRecipeByCuisine(cuisine, diet)

}