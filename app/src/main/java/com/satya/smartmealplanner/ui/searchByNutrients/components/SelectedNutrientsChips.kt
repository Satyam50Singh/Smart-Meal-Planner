package com.satya.smartmealplanner.ui.searchByNutrients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.satya.smartmealplanner.data.model.recipeByNutrients.NutrientRange

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedNutrientsChips(nutrientRange: NutrientRange, onClick: (Boolean) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            listOfNotNull(
                nutrientRange.carbs?.let { "Carbs ${it.start.toInt()} - ${it.endInclusive.toInt()}g" },
                nutrientRange.protein?.let { "Protein ${it.start.toInt()} - ${it.endInclusive.toInt()}g" },
                nutrientRange.calories?.let { "Calories ${it.start.toInt()} - ${it.endInclusive.toInt()}KCal" },
                nutrientRange.fat?.let { "Fat ${it.start.toInt()} - ${it.endInclusive.toInt()}g" }
            )
        ) {
            AssistChip(
                onClick = {
                    onClick(true)
                },
                label = { Text(text = it) }
            )
        }
    }
}
