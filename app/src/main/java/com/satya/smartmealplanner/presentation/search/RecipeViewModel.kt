package com.satya.smartmealplanner.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.smartmealplanner.BuildConfig
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
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

    fun getCategoryList(): List<DashboardCategory> {
        return arrayListOf(
            DashboardCategory(
                1,
                "Search\nRestaurants",
                R.drawable.restaurant,
                "Discover restaurants near you that match your taste. Search by food type, rating, or location."
            ), DashboardCategory(
                2,
                "Search\nBy Ingredients",
                R.drawable.search,
                "Find recipes based on the ingredients you already have at home. Save time and reduce food waste!"
            ),
            DashboardCategory(
                3,
                "Search\nBy Nutrients",
                R.drawable.nutrient,
                "Looking for high-protein, low-carb, or vitamin-rich meals? Get personalized recipes by nutritional content."
            ),
            DashboardCategory(
                4,
                "Search\nBy Cuisines",
                R.drawable.cuisine,
                "Explore diverse world cuisines – Italian, Mexican, Indian, Thai, and more. Find authentic meals by culture."
            ), DashboardCategory(
                5,
                "Random\nRecipes",
                R.drawable.search,
                "Feeling spontaneous? Get inspired with completely random meals."
            ),
            DashboardCategory(
                6,
                "Meal\nPlanner",
                R.drawable.nutrient,
                "Plan your breakfast, lunch, and dinner for the whole week with Spoonacular's meal planner."
            ),
            DashboardCategory(
                7,
                "Find\nSimilar Recipes",
                R.drawable.cuisine,
                "Found a recipe you like? Discover similar ones instantly with a single click."
            ),
            DashboardCategory(
                8,
                "Recipe\nInformation",
                R.drawable.search,
                "Get detailed nutritional and preparation info for any Spoonacular recipe by ID."
            ),
            DashboardCategory(
                10,
                "Guess\nNutrition by Image",
                R.drawable.search,
                "Upload a food image and let AI predict the nutrition facts — calories, protein, fat, and more."
            )
        )
    }
}
