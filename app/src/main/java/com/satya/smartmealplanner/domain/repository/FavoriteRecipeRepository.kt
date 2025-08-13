package com.satya.smartmealplanner.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteRecipeRepository {

    suspend fun saveFavoriteRecipe(recipeId: Int, recipeData: Map<String, Any>): Boolean

    suspend fun deleteFavoriteRecipe(recipeId: Int): Boolean

    suspend fun getAllFavoriteRecipes(): Flow<List<Map<String, Any?>>>

    suspend fun isRecipeFavorite(recipeId: Int): Boolean

}
