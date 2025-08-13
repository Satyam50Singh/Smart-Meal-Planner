package com.satya.smartmealplanner.domain.usecase

import com.satya.smartmealplanner.domain.repository.FavoriteRecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRecipeUseCase @Inject constructor(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
) {
    suspend fun saveFavoriteRecipe(recipeId: Int, recipeData: Map<String, Any>): Boolean =
        favoriteRecipeRepository.saveFavoriteRecipe(recipeId, recipeData)

    suspend fun deleteFavoriteRecipe(recipeId: Int): Boolean =
        favoriteRecipeRepository.deleteFavoriteRecipe(recipeId)

    suspend fun isRecipeFavorite(recipeId: Int): Boolean =
        favoriteRecipeRepository.isRecipeFavorite(recipeId)

    suspend fun getAllFavoriteRecipes(): Flow<List<Map<String, Any?>>> =
        favoriteRecipeRepository.getAllFavoriteRecipes()

}