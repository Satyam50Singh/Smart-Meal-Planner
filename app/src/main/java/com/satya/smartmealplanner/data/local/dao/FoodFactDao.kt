package com.satya.smartmealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.satya.smartmealplanner.data.local.entity.FoodFactEntity


@Dao
interface FoodFactDao {

    @Query("Select * from food_fact where type= :type")
    suspend fun getFoodFactByType(type: String): FoodFactEntity?

    @Query("Delete from food_fact where type= :type")
    suspend fun deleteFoodFactByType(type: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodFact(foodFactEntity: FoodFactEntity)

}