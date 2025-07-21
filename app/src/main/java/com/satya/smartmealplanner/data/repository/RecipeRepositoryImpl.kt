package com.satya.smartmealplanner.data.repository

import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.remote.ApiService
import com.satya.smartmealplanner.domain.model.Resource
import com.satya.smartmealplanner.domain.repository.RecipeRepository
import com.satya.smartmealplanner.utils.parseErrorBody
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

    override suspend fun getRandomJoke(): Resource<RandomJoke?> {
        val response = apiService.fetchRandomJoke()

        return when {
            response.isSuccessful -> {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Something went wrong")
            }

            response.errorBody() != null -> {
                parseErrorBody<RandomJoke?>(response.errorBody(), response.code())
            }

            else -> {
                Resource.Error("Something went wrong")
            }
        }
    }

    override suspend fun getRandomTrivia(): Resource<FoodTrivia?> {
        val response = apiService.fetchRandomTrivia()

        return when {
            response.isSuccessful -> {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Something went wrong")
            }

            response.errorBody() != null -> {
                parseErrorBody<FoodTrivia?>(response.errorBody(), response.code())
            }

            else -> {
                Resource.Error("Something went wrong")
            }
        }
    }

    override suspend fun fetchRandomRecipes(): Resource<RandomRecipes?> {
        val response = apiService.fetchRandomRecipes()

        return when {
            response.isSuccessful -> {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Something went wrong")
            }

            response.errorBody() != null -> {
                parseErrorBody<RandomRecipes?>(response.errorBody(), response.code())
            }

            else -> {
                Resource.Error("Something went wrong")
            }
        }
    }
}

