package com.satya.smartmealplanner.presentation.search

import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails

data class RecipeState(
    val isLoading: Boolean = false,
    val recipes: FindByIngredientsResponse = FindByIngredientsResponse(),
    val error: String? = null
)

data class SelectedRecipeState(
    val isLoading: Boolean = false,
    val recipe: SelectedRecipeDetails? = null,
    val error: String? = null
)

data class RecipeByCuisineState(
    val isLoading: Boolean = false,
    val recipes: RecipeByCuisine? = null,
    val error: String? = null
)

data class RecipeByNutrientsState(
    val isLoading: Boolean = false,
    val recipes: RecipeByNutrients? = null,
    val error: String? = null
)

data class RandomJokeState(
    val isLoading: Boolean = false,
    val randomJoke: RandomJoke? = null,
    val error: String? = null
)

data class FoodTriviaState(
    val isLoading: Boolean = false,
    val foodTrivia: FoodTrivia? = null,
    val error: String? = null
)

data class State<T>(
    val isLoading: Boolean = false,
    val isSuccess: T? = null,
    val isError: String? = null
)