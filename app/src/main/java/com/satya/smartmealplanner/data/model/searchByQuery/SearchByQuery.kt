package com.satya.smartmealplanner.data.model.searchByQuery

data class SearchByQuery(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)