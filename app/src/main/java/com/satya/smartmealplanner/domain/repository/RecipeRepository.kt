package com.satya.smartmealplanner.domain.repository

import com.satya.smartmealplanner.data.local.RandomRecipeEntity
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.domain.model.Resource

interface RecipeRepository {

    suspend fun findByIngredients(
        ingredients: String,
        number: Int,
        apiKey: String
    ): FindByIngredientsResponse

    suspend fun getRecipeDetailsById(recipeId: Int): SelectedRecipeDetails

    suspend fun getRecipeByCuisine(cuisine: String, diet: String): RecipeByCuisine

    suspend fun getRecipeByNutrients(
        minCarbs: Int,
        maxCarbs: Int,
        minProtein: Int,
        maxProtein: Int,
        minCalories: Int,
        maxCalories: Int,
        minFat: Int,
        maxFat: Int
    ): RecipeByNutrients

    suspend fun getRandomJoke(): Resource<RandomJoke?>

    suspend fun getRandomTrivia(): Resource<FoodTrivia?>

    suspend fun fetchRandomRecipes(): Resource<RandomRecipes?>

    suspend fun fetchRandomRecipesFromDb(): Resource<List<RandomRecipeEntity>>

}