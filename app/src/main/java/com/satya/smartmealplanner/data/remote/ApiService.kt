package com.satya.smartmealplanner.data.remote

import com.satya.smartmealplanner.BuildConfig
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.model.searchByQuery.SearchByQuery
import retrofit2.Response
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

    @GET("recipes/findByNutrients")
    suspend fun findByNutrients(
        @Query("minCarbs") minCarbs: Int,
        @Query("maxCarbs") maxCarbs: Int,
        @Query("minProtein") minProtein: Int,
        @Query("maxProtein") maxProtein: Int,
        @Query("minCalories") minCalories: Int,
        @Query("maxCalories") maxCalories: Int,
        @Query("minFat") minFat: Int,
        @Query("maxFat") maxFat: Int,
        @Query("number") number: Int = 20,
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY
    ): RecipeByNutrients

    @GET("food/jokes/random")
    suspend fun fetchRandomJoke(
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY
    ) : Response<RandomJoke>

    @GET("food/trivia/random")
    suspend fun fetchRandomTrivia(
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY
    ): Response<FoodTrivia>

    @GET("recipes/random")
    suspend fun fetchRandomRecipes(
        @Query("number") number: Int = 6,
        @Query("limitLicense") limitLicense: Boolean = true,
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY
    ): Response<RandomRecipes>


    @GET("recipes/complexSearch")
    suspend fun fetchRecipesByQuery(
        @Query("query") query: String,
        @Query("number") number: Int = 9,
        @Query("type") type: String = "main course",
        @Query("diet") diet: String = "vegetarian",
        @Query("apiKey") apiKey: String = BuildConfig.SPOONACULAR_API_KEY
    ): Response<SearchByQuery>

}