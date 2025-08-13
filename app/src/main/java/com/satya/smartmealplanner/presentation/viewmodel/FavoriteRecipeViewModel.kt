package com.satya.smartmealplanner.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.smartmealplanner.domain.model.State
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


    var favoriteRecipeState by mutableStateOf(State<List<Map<String, Any?>>>())
        private set

    fun getAllFavoriteRecipes() {
        favoriteRecipeState = favoriteRecipeState.copy(isLoading = true, isError = null)

        viewModelScope.launch {
            try {
                val data = favoriteRecipeUseCase.getAllFavoriteRecipes()
                favoriteRecipeState = if (data.isNotEmpty()) {
                    favoriteRecipeState.copy(
                        isLoading = false,
                        isSuccess = data,
                        isError = null
                    )
                } else {
                    favoriteRecipeState.copy(
                        isLoading = false,
                        isSuccess = emptyList(),
                        isError = "No data found!"
                    )
                }
            } catch (e: Exception) {
                favoriteRecipeState.copy(isError = "Something went wrong!", isLoading = false)
                e.printStackTrace()
            }
        }
    }
}