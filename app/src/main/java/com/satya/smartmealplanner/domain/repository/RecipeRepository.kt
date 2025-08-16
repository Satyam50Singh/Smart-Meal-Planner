package com.satya.smartmealplanner.domain.repository

import com.satya.smartmealplanner.data.local.entity.FoodFactEntity
import com.satya.smartmealplanner.data.local.entity.RandomRecipeEntity
import com.satya.smartmealplanner.data.model.autoCompleteIngredients.AutoCompleteIngredientsItem
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponseItem
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrientsItem
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.model.searchByQuery.SearchByQuery
import com.satya.smartmealplanner.data.model.similarRecipes.SimilarRecipesByIdItem
import com.satya.smartmealplanner.data.model.weeklyMealPlan.WeeklyMealPlan
import com.satya.smartmealplanner.domain.model.Resource

interface RecipeRepository {

    suspend fun findByIngredients(
        ingredients: String, number: Int, apiKey: String
    ): Resource<List<FindByIngredientsResponseItem>>

    suspend fun getRecipeDetailsById(recipeId: Int): Resource<SelectedRecipeDetails>

    suspend fun getRecipeByCuisine(cuisine: String, diet: String): Resource<RecipeByCuisine>

    suspend fun getRecipeByNutrients(
        minCarbs: Int,
        maxCarbs: Int,
        minProtein: Int,
        maxProtein: Int,
        minCalories: Int,
        maxCalories: Int,
        minFat: Int,
        maxFat: Int
    ): Resource<List<RecipeByNutrientsItem>>

    suspend fun getRandomJoke(): Resource<RandomJoke?>

    suspend fun getRandomJokeFromDb(type: String): Resource<FoodFactEntity>

    suspend fun getRandomTrivia(): Resource<FoodTrivia?>

    suspend fun getRandomTriviaFromDb(type: String): Resource<FoodFactEntity>

    suspend fun fetchRandomRecipes(): Resource<RandomRecipes?>

    suspend fun fetchRandomRecipesFromDb(): Resource<List<RandomRecipeEntity>>

    suspend fun fetchRecipeByQuery(searchQuery: String, isVeg: Boolean): Resource<SearchByQuery?>

    suspend fun fetchAutoCompleteIngredients(query: String): Resource<List<AutoCompleteIngredientsItem>?>

    suspend fun fetchSimilarRecipesById(recipeId: Int): Resource<List<SimilarRecipesByIdItem>?>

    suspend fun generateWeeklyMealPlan(
        loadApi: Boolean, timeFrame: String, targetCalories: Int, diet: String, exclude: String
    ): Resource<WeeklyMealPlan?>
}