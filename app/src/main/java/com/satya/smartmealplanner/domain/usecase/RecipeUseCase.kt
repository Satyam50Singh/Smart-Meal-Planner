package com.satya.smartmealplanner.domain.usecase

import com.satya.smartmealplanner.data.local.entity.FoodFactEntity
import com.satya.smartmealplanner.data.local.entity.RandomRecipeEntity
import com.satya.smartmealplanner.data.local.entity.toDomain
import com.satya.smartmealplanner.data.model.autoCompleteIngredients.AutoCompleteIngredients
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.searchByQuery.SearchByQuery
import com.satya.smartmealplanner.domain.model.Resource
import com.satya.smartmealplanner.domain.repository.RecipeRepository
import com.satya.smartmealplanner.utils.Constants
import javax.inject.Inject

class RecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(
        ingredients: String, number: Int, apiKey: String
    ) = repository.findByIngredients(ingredients, number, apiKey)


    suspend fun getRecipeDetailsById(recipeId: Int) = repository.getRecipeDetailsById(recipeId)

    suspend fun getRecipeByCuisine(cuisine: String, diet: String): RecipeByCuisine =
        repository.getRecipeByCuisine(cuisine, diet)

    suspend fun getRecipeByNutrients(
        minCarbs: Int,
        maxCarbs: Int,
        minProtein: Int,
        maxProtein: Int,
        minCalories: Int,
        maxCalories: Int,
        minFat: Int,
        maxFat: Int
    ): RecipeByNutrients = repository.getRecipeByNutrients(
        minCarbs, maxCarbs, minProtein, maxProtein, minCalories, maxCalories, minFat, maxFat
    )

    suspend fun getRandomJoke(forceRefresh: Boolean): Resource<RandomJoke?> {
        return if (forceRefresh) {
            repository.getRandomJoke()
        } else {
            val savedJoke: Resource<FoodFactEntity> = repository.getRandomJokeFromDb(type = Constants.JOKE)
            if (savedJoke is Resource.Success) {
                Resource.Success(RandomJoke(savedJoke.data.text))
            } else {
                repository.getRandomJoke()
            }
        }
    }

    suspend fun getRandomTrivia(forceRefresh: Boolean): Resource<FoodTrivia?> {
        return if (forceRefresh) {
            repository.getRandomTrivia()
        } else {
            val savedTrivia: Resource<FoodFactEntity> = repository.getRandomTriviaFromDb(type = Constants.TRIVIA)
            if (savedTrivia is Resource.Success) {
                Resource.Success(FoodTrivia(savedTrivia.data.text))
            } else {
                repository.getRandomTrivia()
            }
        }
    }

    suspend fun getRandomRecipes(forceRefresh: Boolean = false): Resource<RandomRecipes?> {
        return if (forceRefresh) {
            repository.fetchRandomRecipes()
        } else {
            val savedRecipes: Resource<List<RandomRecipeEntity>> =
                repository.fetchRandomRecipesFromDb()
            if (savedRecipes is Resource.Success && savedRecipes.data.isNotEmpty()) {
                val mappedResult = savedRecipes.mapToRawRandomRecipes()
                mappedResult
            } else {
                repository.fetchRandomRecipes()
            }
        }
    }

    private fun Resource<List<RandomRecipeEntity>>.mapToRawRandomRecipes(): Resource<RandomRecipes?> {
        return when (this) {
            is Resource.Success -> {
                val randomRecipes = this.data.map { it.toDomain() }
                Resource.Success(RandomRecipes(randomRecipes))
            }

            is Resource.Error -> Resource.Error(this.message)
        }
    }

    suspend fun fetchRecipesByQuery(searchQuery: String, isVeg: Boolean): Resource<SearchByQuery?> = repository.fetchRecipeByQuery(searchQuery, isVeg)

    suspend fun fetchAutoCompleteIngredients(query: String): Resource<List<String>?> {
        val response: Resource<AutoCompleteIngredients?> = repository.fetchAutoCompleteIngredients(query)
        return when (response) {
            is Resource.Success -> {
                val ingredients = response.data?.map { it.name }
                Resource.Success(ingredients)
            }
            is Resource.Error -> Resource.Error(response.message)
        }
    }

    suspend fun fetchSimilarRecipesById(recipeId: Int) = repository.fetchSimilarRecipesById(recipeId)

    suspend fun generateWeeklyMealPlan(
        loadApi: Boolean,
        timeFrame: String,
        targetCalories: Int,
        diet: String,
        exclude: String
    ) = repository.generateWeeklyMealPlan(loadApi, timeFrame, targetCalories, diet, exclude)
}
