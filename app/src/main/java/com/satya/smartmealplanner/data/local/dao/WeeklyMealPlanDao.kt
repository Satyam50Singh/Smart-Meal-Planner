package com.satya.smartmealplanner.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.satya.smartmealplanner.data.local.entity.WeeklyMealPlanEntity

@Dao
interface WeeklyMealPlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyMealPlan(weeklyMealPlan: WeeklyMealPlanEntity)

    @Query("Select * from weekly_meal_plan WHERE id = 1")
    suspend fun getWeeklyMealPlan(): WeeklyMealPlanEntity?

    @Query("DELETE FROM weekly_meal_plan")
    suspend fun deleteWeeklyMealPlan()

}