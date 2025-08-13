package com.satya.smartmealplanner.domain.model

data class State<T>(
    val isLoading: Boolean = false,
    val isSuccess: T? = null,
    val isError: String? = null
)