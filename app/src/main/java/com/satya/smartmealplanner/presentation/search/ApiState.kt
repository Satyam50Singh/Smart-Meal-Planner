package com.satya.smartmealplanner.presentation.search

data class State<T>(
    val isLoading: Boolean = false,
    val isSuccess: T? = null,
    val isError: String? = null
)