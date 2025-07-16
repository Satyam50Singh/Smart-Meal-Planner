package com.satya.smartmealplanner.di

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
    fun provideRecipeRepository(apiService: ApiService): RecipeRepository {
        return RecipeRepositoryImpl(apiService)
    }

}