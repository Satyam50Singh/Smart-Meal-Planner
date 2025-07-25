package com.satya.smartmealplanner.di

import com.satya.smartmealplanner.data.local.RandomRecipeDao
import com.satya.smartmealplanner.data.preferences.SharedPreferencesManager
import com.satya.smartmealplanner.data.remote.ApiService
import com.satya.smartmealplanner.data.repository.RecipeRepositoryImpl
import com.satya.smartmealplanner.domain.repository.RecipeRepository
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
        prefs: SharedPreferencesManager
    ): RecipeRepository {
        return RecipeRepositoryImpl(apiService, randomRecipeDao, prefs)
    }

}