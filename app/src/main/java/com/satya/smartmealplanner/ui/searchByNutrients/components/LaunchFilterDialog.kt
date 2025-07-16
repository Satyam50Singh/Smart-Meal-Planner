package com.satya.smartmealplanner.ui.searchByNutrients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.satya.smartmealplanner.data.model.recipeByNutrients.NutrientRange

@Composable
fun LaunchFilterDialog(
    nutrientRange: NutrientRange,
    onDismiss: () -> Unit,
    onApply: (NutrientRange, Boolean) -> Unit
) {
    var carbsRange by remember { mutableStateOf(nutrientRange.carbs) }
    var proteinRange by remember { mutableStateOf(nutrientRange.protein) }
    var caloriesRange by remember { mutableStateOf(nutrientRange.calories) }
    var fatRange by remember { mutableStateOf(nutrientRange.fat) }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                Text(
                    text = "Select Nutrients",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.padding(16.dp)) {
                    Column {
                        Text(
                            text = "Carbs",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 16.dp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${carbsRange.start.toInt()}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${carbsRange.endInclusive.toInt()}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        RangeSlider(
                            value = carbsRange,
                            onValueChange = { carbsRange = it },
                            steps = 9,
                            valueRange = 0f..100f,
                            modifier = Modifier.height(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Text(
                            text = "Protein",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 16.dp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${proteinRange.start.toInt()}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${proteinRange.endInclusive.toInt()}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        RangeSlider(
                            value = proteinRange,
                            onValueChange = { proteinRange = it },
                            steps = 9,
                            valueRange = 0f..100f,
                            modifier = Modifier.height(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Calories",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 16.dp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${caloriesRange.start.toInt()}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${caloriesRange.endInclusive.toInt()}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        RangeSlider(
                            value = caloriesRange,
                            onValueChange = { caloriesRange = it },
                            valueRange = 100f..800f,
                            modifier = Modifier.height(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Column {
                        Text(
                            text = "Fat",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 16.dp),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${fatRange.start.toInt()}", fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${fatRange.endInclusive.toInt()}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                        RangeSlider(
                            value = fatRange,
                            onValueChange = { fatRange = it },
                            steps = 9,
                            valueRange = 1f..100f,
                            modifier = Modifier.height(24.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            onClick = { onDismiss() },
                            modifier = Modifier.padding(4.dp),
                        ) {
                            Text("Close")
                        }
                        TextButton(
                            onClick = {
                                val isChanged = carbsRange != nutrientRange.carbs ||
                                        proteinRange != nutrientRange.protein ||
                                        caloriesRange != nutrientRange.calories ||
                                        fatRange != nutrientRange.fat

                                onApply(
                                    NutrientRange(
                                        carbs = carbsRange,
                                        protein = proteinRange,
                                        calories = caloriesRange,
                                        fat = fatRange
                                    ),
                                    isChanged
                                )
                            },
                            modifier = Modifier.padding(4.dp),
                        ) {
                            Text("Apply")
                        }
                    }
                }
            }
        }
    }
}



