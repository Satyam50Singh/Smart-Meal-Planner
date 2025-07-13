package com.satya.smartmealplanner.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor : Interceptor {

    private val TAG = "ResponseInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            401 -> {
                Log.e(TAG, "${response.code} Unauthorized: Token may be expired.")
            }

            403 -> {
                Log.e(TAG, "${response.code} Forbidden: You don't have access.")
            }

            500 -> {
                Log.e(TAG, "${response.code} Server error: Something went wrong.")
            }

            in 400..499 -> {
                Log.e(TAG, "${response.code} Client error: ${response.message}")
            }

            in 500..599 -> {
                Log.e(TAG, "${response.code} Server error: ${response.message}")
            }
        }

        return response
    }
}