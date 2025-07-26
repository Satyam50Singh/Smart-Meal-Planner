package com.satya.smartmealplanner.domain.repository

import com.satya.smartmealplanner.data.local.entity.FoodFactEntity
import com.satya.smartmealplanner.data.local.entity.RandomRecipeEntity
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.model.searchByQuery.SearchByQuery
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

    suspend fun getRandomJokeFromDb(type: String): Resource<FoodFactEntity>

    suspend fun getRandomTrivia(): Resource<FoodTrivia?>

    suspend fun getRandomTriviaFromDb(type: String): Resource<FoodFactEntity>

    suspend fun fetchRandomRecipes(): Resource<RandomRecipes?>

    suspend fun fetchRandomRecipesFromDb(): Resource<List<RandomRecipeEntity>>

    suspend fun fetchRecipeByQuery(searchQuery: String): Resource<SearchByQuery?>

}