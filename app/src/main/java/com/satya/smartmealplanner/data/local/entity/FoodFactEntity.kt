package com.satya.smartmealplanner.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food_fact")
data class FoodFactEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val type: String
)