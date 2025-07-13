package com.satya.smartmealplanner.data.remote

import com.satya.smartmealplanner.BuildConfig
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
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

    @GET("recipes/complexSearch")
    suspend fun getRecipeByCuisine(
        @Query("cuisine") cuisine: String,
        @Query("diet") diet: String,
        @Query("query") query: String = "",
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true,
        @Query("sortDirection") sortDirection: String = "asc",
        @Query("number") number: Int = 20,
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY
    ): RecipeByCuisine

}