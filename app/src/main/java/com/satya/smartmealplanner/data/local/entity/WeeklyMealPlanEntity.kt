package com.satya.smartmealplanner.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.satya.smartmealplanner.data.model.weeklyMealPlan.Week
import com.satya.smartmealplanner.data.model.weeklyMealPlan.WeeklyMealPlan

@Entity("weekly_meal_plan")
data class WeeklyMealPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo("week_data")
    val week: Week
) {
    fun toDomain(): WeeklyMealPlan {
        return WeeklyMealPlan(
            week = week
        )
    }
}