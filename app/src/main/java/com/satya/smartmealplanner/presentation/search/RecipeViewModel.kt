package com.satya.smartmealplanner.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.smartmealplanner.BuildConfig
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
}