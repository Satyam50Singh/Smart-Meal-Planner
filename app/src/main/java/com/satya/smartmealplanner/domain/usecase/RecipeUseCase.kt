package com.satya.smartmealplanner.domain.usecase

import com.satya.smartmealplanner.data.local.RandomRecipeEntity
import com.satya.smartmealplanner.data.local.toDomain
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.domain.model.Resource
import com.satya.smartmealplanner.domain.repository.RecipeRepository
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

    suspend fun getRandomJoke(): Resource<RandomJoke?> = repository.getRandomJoke()

    suspend fun getRandomTrivia(): Resource<FoodTrivia?> = repository.getRandomTrivia()

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
}
