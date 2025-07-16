package com.satya.smartmealplanner.data.model.recipeDetails

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatche>
)