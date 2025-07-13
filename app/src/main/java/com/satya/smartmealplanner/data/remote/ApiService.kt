package com.satya.smartmealplanner.data.remote

import com.satya.smartmealplanner.BuildConfig
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/findByIngredients")
    suspend fun findByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY
    ): FindByIngredientsResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetailsById(
        @Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY,
        @Query("addWinePairing") addWinePairing: Boolean = true
    ): SelectedRecipeDetails

}