package com.satya.smartmealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.satya.smartmealplanner.data.local.entity.RandomRecipeEntity

@Dao
interface RandomRecipeDao {

    @Query("Select * from random_recipes")
    suspend fun getAllRandomRecipes(): List<RandomRecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRandomRecipes(randomRecipes: List<RandomRecipeEntity>)

    @Query("Delete from random_recipes")
    suspend fun deleteAllRandomRecipes()

}