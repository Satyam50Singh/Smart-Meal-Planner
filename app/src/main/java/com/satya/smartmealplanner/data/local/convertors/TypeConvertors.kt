package com.satya.smartmealplanner.data.local.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.satya.smartmealplanner.data.model.weeklyMealPlan.Meal
import com.satya.smartmealplanner.data.model.weeklyMealPlan.MealDay
import com.satya.smartmealplanner.data.model.weeklyMealPlan.Nutrients
import com.satya.smartmealplanner.data.model.weeklyMealPlan.Week

class TypeConvertors {

    private val gson = Gson()

    @TypeConverter
    fun fromWeek(value: Week): String = gson.toJson(value)

    @TypeConverter
    fun toWeek(value: String): Week = gson.fromJson(value, Week::class.java)

    @TypeConverter
    fun fromMealDay(value: MealDay) = gson.toJson(value)

    @TypeConverter
    fun toMealDay(value: String): MealDay = gson.fromJson(value, MealDay::class.java)

    @TypeConverter
    fun fromNutrients(value: Nutrients) = gson.toJson(value)

    @TypeConverter
    fun toNutrients(value: String): Nutrients = gson.fromJson(value, Nutrients::class.java)

    @TypeConverter
    fun fromMeal(value: List<Meal>) = gson.toJson(value)

    @TypeConverter
    fun toMeal(value: String): List<Meal> =
        gson.fromJson(value, object : TypeToken<List<Meal>>() {}.type)

}