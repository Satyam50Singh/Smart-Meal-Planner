package com.satya.smartmealplanner.di

import com.google.firebase.firestore.FirebaseFirestore
import com.satya.smartmealplanner.data.local.dao.FoodFactDao
import com.satya.smartmealplanner.data.local.dao.RandomRecipeDao
import com.satya.smartmealplanner.data.local.dao.WeeklyMealPlanDao
import com.satya.smartmealplanner.data.preferences.SharedPreferencesManager
import com.satya.smartmealplanner.data.remote.ApiService
import com.satya.smartmealplanner.data.repository.FavoriteRecipeRepositoryImpl
import com.satya.smartmealplanner.data.repository.RecipeRepositoryImpl
import com.satya.smartmealplanner.domain.repository.FavoriteRecipeRepository
import com.satya.smartmealplanner.domain.repository.RecipeRepository
import com.satya.smartmealplanner.utils.DeviceIdProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRecipeRepository(
        apiService: ApiService,
        randomRecipeDao: RandomRecipeDao,
        foodFactDao: FoodFactDao,
        weeklyMealPlanDao: WeeklyMealPlanDao,
        prefs: SharedPreferencesManager
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            apiService, randomRecipeDao, foodFactDao, weeklyMealPlanDao, prefs
        )
    }

    @Provides
    fun provideFavoriteRecipeRepository(
        firebaseFirestore: FirebaseFirestore,
        deviceIdProvider: DeviceIdProvider
    ): FavoriteRecipeRepository {
        return FavoriteRecipeRepositoryImpl(firebaseFirestore, deviceIdProvider)
    }

}