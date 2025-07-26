package com.satya.smartmealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.satya.smartmealplanner.data.local.dao.FoodFactDao
import com.satya.smartmealplanner.data.local.dao.RandomRecipeDao
import com.satya.smartmealplanner.data.local.entity.FoodFactEntity
import com.satya.smartmealplanner.data.local.entity.RandomRecipeEntity

@Database(
    entities = [RandomRecipeEntity::class, FoodFactEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun randomRecipeDao(): RandomRecipeDao
    abstract fun foodFactDao(): FoodFactDao
}