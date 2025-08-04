package com.satya.smartmealplanner.ui.weeklyMealPlanner.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.satya.smartmealplanner.data.model.weeklyMealPlan.Nutrients
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun NutritionDonutChart(nutrients: Nutrients) {
    val slices = listOf(
        Pie(
            label = "Protein",
            data = nutrients.protein,
            color = Color(0xFF4CAF50)
        ),
        Pie(
            label = "Fat",
            data = nutrients.fat,
            color = Color(0xFFFFC107)
        ),
        Pie(
            label = "Carbs",
            data = nutrients.carbohydrates,
            color = Color(0xFF03A9F4)
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {

        PieChart(
            modifier = Modifier.size(220.dp),
            data = slices,
            selectedScale = 2f,
            style = Pie.Style.Stroke(width = 60.dp),
            onPieClick = { }
        )

        Text(
            text = "${nutrients.calories.toInt()} kcal",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        slices.forEach { pie ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(pie.color, shape = CircleShape)
                )
                pie.label?.let { label ->
                    Text(
                        text = "$label - ${"%.1f".format(pie.data)}g",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

}
