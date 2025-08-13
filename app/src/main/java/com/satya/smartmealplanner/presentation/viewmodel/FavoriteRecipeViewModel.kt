package com.satya.smartmealplanner.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.smartmealplanner.domain.usecase.FavoriteRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipeViewModel @Inject constructor(
    private val favoriteRecipeUseCase: FavoriteRecipeUseCase
) : ViewModel() {

    var saveFavoriteRecipeState by mutableStateOf(false)
        private set

    fun saveFavoriteRecipe(recipeId: Int, recipeData: Map<String, Any>) {
        viewModelScope.launch {
            try {
                saveFavoriteRecipeState =
                    favoriteRecipeUseCase.saveFavoriteRecipe(recipeId, recipeData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteFavoriteRecipe(recipeId: Int) {
        viewModelScope.launch {
            try {
                saveFavoriteRecipeState = !favoriteRecipeUseCase.deleteFavoriteRecipe(recipeId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun isRecipeFavorite(recipeId: Int) {
        viewModelScope.launch {
            try {
                saveFavoriteRecipeState = favoriteRecipeUseCase.isRecipeFavorite(recipeId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}