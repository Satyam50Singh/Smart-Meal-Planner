package com.satya.smartmealplanner.data.repository

import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.remote.ApiService
import com.satya.smartmealplanner.domain.repository.RecipeRepository
import retrofit2.Response
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

}