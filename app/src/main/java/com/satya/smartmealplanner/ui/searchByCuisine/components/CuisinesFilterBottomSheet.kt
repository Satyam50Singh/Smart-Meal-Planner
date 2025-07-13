package com.satya.smartmealplanner.ui.searchByCuisine.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CuisinesFilterBottomSheet(
    onApplyClick: (String, String) -> Unit,
    onDismiss: () -> Unit
) {

    val allCuisines = getListOfCuisines()
    val allDiets = getListOfDiets()

    var selectedCuisine by remember { mutableStateOf("") }
    var selectedDiets by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Column(modifier = Modifier.padding(16.dp)) {


            Text(
                text = "Select Diet",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                allDiets.forEach { diet ->
                    FilterChip(
                        selected = selectedDiets == diet,
                        onClick = {
                            selectedDiets = diet
                        },
                        label = { Text(diet) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Select Cuisine",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                allCuisines.forEach { cuisine ->
                    FilterChip(
                        selected = selectedCuisine == cuisine,
                        onClick = {
                            selectedCuisine = cuisine
                        },
                        label = { Text(cuisine) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onApplyClick(selectedCuisine, selectedDiets)
                },
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Text("Apply Filter")
            }
        }
    }
}

fun getListOfDiets(): List<String> {
    return listOf(
        "Vegetarian",
        "Vegan",
        "Gluten-Free",
        "Paleo",
        "Ketogenic",
        "Lacto-Vegetarian",
        "Ovo-Vegetarian",
        "Pescetarian",
        "Whole30",
        "Low FODMAP"
    )
}


fun getListOfCuisines(): List<String> {
    return listOf(
        "Indian",
        "Italian",
        "Chinese",
        "Mexican",
        "American",
        "Thai",
        "Japanese",
        "French",
        "Mediterranean",
        "Greek"
    )
}