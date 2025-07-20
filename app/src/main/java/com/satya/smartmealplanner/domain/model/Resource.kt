package com.satya.smartmealplanner.domain.model


sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}