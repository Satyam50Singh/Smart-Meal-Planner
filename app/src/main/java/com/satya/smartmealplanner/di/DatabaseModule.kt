package com.satya.smartmealplanner.di

import android.content.Context
import androidx.room.Room
import com.satya.smartmealplanner.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "app_database"
        ).build()
    }

    @Provides
    fun providesRandomRecipeDao(appDatabase: AppDatabase) = appDatabase.randomRecipeDao()

    @Provides
    fun providesFoodFactDao(appDatabase: AppDatabase) = appDatabase.foodFactDao()

    @Provides
    fun providesWeeklyMealPlanDao(appDatabase: AppDatabase) = appDatabase.weeklyMealPlanDao()

}