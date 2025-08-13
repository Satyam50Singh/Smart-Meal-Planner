package com.satya.smartmealplanner.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.satya.smartmealplanner.domain.repository.FavoriteRecipeRepository
import com.satya.smartmealplanner.utils.Constants
import com.satya.smartmealplanner.utils.DeviceIdProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteRecipeRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val deviceIdProvider: DeviceIdProvider
) : FavoriteRecipeRepository {

    override suspend fun saveFavoriteRecipe(recipeId: Int, recipeData: Map<String, Any>): Boolean {
        return try {
            val deviceId = deviceIdProvider.getDeviceId()
            firebaseFirestore.collection(Constants.USERS)
                .document(deviceId)
                .collection(Constants.FAVORITE_RECIPES)
                .document(recipeId.toString())
                .set(recipeData)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun deleteFavoriteRecipe(recipeId: Int): Boolean {
        return try {
            firebaseFirestore
                .collection(Constants.USERS)
                .document(deviceIdProvider.getDeviceId())
                .collection(Constants.FAVORITE_RECIPES)
                .document(recipeId.toString())
                .delete()
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getAllFavoriteRecipes(): Flow<List<Map<String, Any?>>> = callbackFlow {

        val listener = firebaseFirestore
            .collection(Constants.USERS)
            .document(deviceIdProvider.getDeviceId())
            .collection(Constants.FAVORITE_RECIPES)
            .addSnapshotListener { snapshot, e ->

                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }

                val recipes = mutableListOf<Map<String, Any?>>()

                snapshot?.documents?.forEach {
                    val recipe = mapOf(
                        "recipeId" to it.get("recipeId"),
                        "recipeTitle" to it.get("recipeTitle"),
                        "recipeImage" to it.get("recipeImage"),
                        "recipeServings" to it.get("recipeServings"),
                        "recipeReadyInMinutes" to it.get("recipeReadyInMinutes")
                    )
                    recipes.add(recipe)
                }

                trySend(recipes)
            }

        awaitClose { listener.remove() }
    }

    override suspend fun isRecipeFavorite(recipeId: Int): Boolean {
        return try {
            val snapshot = firebaseFirestore
                .collection(Constants.USERS)
                .document(deviceIdProvider.getDeviceId())
                .collection(Constants.FAVORITE_RECIPES)
                .document(recipeId.toString())
                .get()
                .await()

            snapshot.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}