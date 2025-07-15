package com.satya.smartmealplanner.data.repository

import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.remote.ApiService
import com.satya.smartmealplanner.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RecipeRepository {

    override suspend fun findByIngredients(
        ingredients: String,
        number: Int,
        apiKey: String
    ): FindByIngredientsResponse {
        return apiService.findByIngredients(ingredients, number, apiKey)
    }

    override suspend fun getRecipeDetailsById(recipeId: Int): SelectedRecipeDetails {
        return apiService.getRecipeDetailsById(recipeId)
    }

    override suspend fun getRecipeByCuisine(
        cuisine: String,
        diet: String
    ): RecipeByCuisine {
        return apiService.getRecipeByCuisine(cuisine, diet)
    }


    override suspend fun getRecipeByNutrients(
        minCarbs: Int,
        maxCarbs: Int,
        minProtein: Int,
        maxProtein: Int,
        minCalories: Int,
        maxCalories: Int,
        minFat: Int,
        maxFat: Int
    ): RecipeByNutrients {
        return apiService.findByNutrients(
            minCarbs,
            maxCarbs,
            minProtein,
            maxProtein,
            minCalories,
            maxCalories,
            minFat,
            maxFat
        )
    }

}