package com.satya.smartmealplanner.di

import android.content.Context
import com.satya.smartmealplanner.data.preferences.SharedPreferencesManager
import com.satya.smartmealplanner.data.preferences.PreferenceRepositoryImpl
import com.satya.smartmealplanner.domain.preferences.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(@ApplicationContext context: Context): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Singleton
    @Provides
    fun provideStringPreferenceRepository(preferencesManager: SharedPreferencesManager): PreferenceRepository {
        return PreferenceRepositoryImpl(preferencesManager)
    }
}