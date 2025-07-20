package com.satya.smartmealplanner.utils

import com.google.gson.Gson
import com.satya.smartmealplanner.domain.model.Resource
import com.satya.smartmealplanner.domain.model.SpoonacularError
import okhttp3.ResponseBody

fun <T> parseErrorBody(errorBody: ResponseBody?, code: Int): Resource.Error<T> {
    return try {
        val errorString = errorBody?.string()
        val spoonacularError = Gson().fromJson(errorString, SpoonacularError::class.java)

        Resource.Error(
            "Status: ${spoonacularError.status ?: "N/A"}\n" +
                    "Error Code: ${spoonacularError.code ?: code}\n" +
                    "Error Message: ${spoonacularError.message ?: "No message"}"
        )
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Error("Something went wrong!")
    }
}