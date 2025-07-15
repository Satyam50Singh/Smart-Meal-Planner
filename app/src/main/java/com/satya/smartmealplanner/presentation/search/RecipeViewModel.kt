package com.satya.smartmealplanner.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.smartmealplanner.BuildConfig
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    var uiState by mutableStateOf(RecipeState())
        private set

    fun findByIngredients(ingredients: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val recipe = recipeUseCase(ingredients, 20, BuildConfig.SPOONACULAR_API_KEY)
                uiState = uiState.copy(recipes = recipe, isLoading = false)
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun clearRecipes() {
        uiState = uiState.copy(recipes = FindByIngredientsResponse())
    }

    fun getCategoryList(): List<DashboardCategory> {
        return arrayListOf(
            DashboardCategory(
                1,
                "Search\nRestaurants",
                R.drawable.restaurant,
                "Discover restaurants near you that match your taste. Search by food type, rating, or location.",
                "search_restaurants"
            ), DashboardCategory(
                2,
                "Search\nBy Ingredients",
                R.drawable.search,
                "Find recipes based on the ingredients you already have at home. Save time and reduce food waste!",
                "search_by_ingredients"
            ), DashboardCategory(
                3,
                "Search\nBy Nutrients",
                R.drawable.nutrient,
                "Looking for high-protein, low-carb, or vitamin-rich meals? Get personalized recipes by nutritional content.",
                "search_by_nutrients"
            ), DashboardCategory(
                4,
                "Search\nBy Cuisines",
                R.drawable.cuisine,
                "Explore diverse world cuisines – Italian, Mexican, Indian, Thai, and more. Find authentic meals by culture.",
                "search_by_cuisines"
            ), DashboardCategory(
                5,
                "Random\nRecipes",
                R.drawable.search,
                "Feeling spontaneous? Get inspired with completely random meals.",
                "random_recipes"
            ), DashboardCategory(
                6,
                "Meal\nPlanner",
                R.drawable.nutrient,
                "Plan your breakfast, lunch, and dinner for the whole week with Spoonacular's meal planner.",
                "meal_planner"
            ), DashboardCategory(
                7,
                "Find\nSimilar Recipes",
                R.drawable.cuisine,
                "Found a recipe you like? Discover similar ones instantly with a single click.",
                "find_similar_recipes"
            ), DashboardCategory(
                8,
                "Recipe\nInformation",
                R.drawable.search,
                "Get detailed nutritional and preparation info for any Spoonacular recipe by ID.",
                "recipe_information"
            ), DashboardCategory(
                10,
                "Guess\nNutrition by Image",
                R.drawable.search,
                "Upload a food image and let AI predict the nutrition facts — calories, protein, fat, and more.",
                "guess_nutrition_by_image"
            )
        )
    }

    var selectedRecipeState by mutableStateOf(SelectedRecipeState())
        private set

    fun getRecipeById(recipeId: Int) {

        viewModelScope.launch {
            selectedRecipeState = selectedRecipeState.copy(isLoading = true)

            try {
                val recipeDetails = recipeUseCase.getRecipeDetailsById(recipeId)
                selectedRecipeState =
                    selectedRecipeState.copy(recipe = recipeDetails, isLoading = false)
            } catch (e: Exception) {
                selectedRecipeState = selectedRecipeState.copy(error = e.message, isLoading = false)
            }
        }

    }

    var recipeByCuisineState by mutableStateOf(RecipeByCuisineState())
        private set

    fun getRecipeByCuisine(cuisine: String, diet: String) {
        viewModelScope.launch {
            recipeByCuisineState = recipeByCuisineState.copy(isLoading = true, error = null)

            try {
                val listOfRecipes: RecipeByCuisine = recipeUseCase.getRecipeByCuisine(cuisine, diet)
                recipeByCuisineState = recipeByCuisineState.copy(
                    isLoading = false, recipes = listOfRecipes, error = null
                )
            } catch (e: Exception) {
                recipeByCuisineState =
                    recipeByCuisineState.copy(error = e.message, isLoading = false)
            }
        }
    }


    var recipeByNutrientsState by mutableStateOf(RecipeByNutrientsState())
        private set

    fun getRecipeByNutrients(
        minCarbs: Int,
        maxCarbs: Int,
        minProtein: Int,
        maxProtein: Int,
        minCalories: Int,
        maxCalories: Int,
        minFat: Int,
        maxFat: Int
    ) {

        viewModelScope.launch {

            recipeByNutrientsState = recipeByNutrientsState.copy(isLoading = true, error = null)

            try {
                val listOfRecipes: RecipeByNutrients = recipeUseCase.getRecipeByNutrients(
                    minCarbs,
                    maxCarbs,
                    minProtein,
                    maxProtein,
                    minCalories,
                    maxCalories,
                    minFat,
                    maxFat
                )
                recipeByNutrientsState = recipeByNutrientsState.copy(
                    isLoading = false, recipes = listOfRecipes, error = null
                )
            } catch (e: Exception) {
                recipeByNutrientsState =
                    recipeByNutrientsState.copy(error = e.message, isLoading = false)
            }
        }

    }
}
