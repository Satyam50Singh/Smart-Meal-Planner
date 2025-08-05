package com.satya.smartmealplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.satya.smartmealplanner.data.local.convertors.TypeConvertors
import com.satya.smartmealplanner.data.local.dao.FoodFactDao
import com.satya.smartmealplanner.data.local.dao.RandomRecipeDao
import com.satya.smartmealplanner.data.local.dao.WeeklyMealPlanDao
import com.satya.smartmealplanner.data.local.entity.FoodFactEntity
import com.satya.smartmealplanner.data.local.entity.RandomRecipeEntity
import com.satya.smartmealplanner.data.local.entity.WeeklyMealPlanEntity

@Database(
    entities = [RandomRecipeEntity::class, FoodFactEntity::class, WeeklyMealPlanEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConvertors::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun randomRecipeDao(): RandomRecipeDao
    abstract fun foodFactDao(): FoodFactDao
    abstract fun weeklyMealPlanDao() : WeeklyMealPlanDao
}