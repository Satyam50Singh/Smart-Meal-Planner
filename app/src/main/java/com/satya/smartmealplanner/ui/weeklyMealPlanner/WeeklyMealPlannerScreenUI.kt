package com.satya.smartmealplanner.ui.weeklyMealPlanner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.satya.smartmealplanner.data.model.weeklyMealPlan.MealDay
import com.satya.smartmealplanner.ui.weeklyMealPlanner.components.CardHeader
import com.satya.smartmealplanner.ui.weeklyMealPlanner.components.DayWiseMealsListing
import com.satya.smartmealplanner.ui.weeklyMealPlanner.components.NutritionDonutChart

@Composable
fun WeeklyMealPlannerScreenUI(week: List<Pair<String, MealDay>>) {
    LazyColumn {
        item {
            week.forEach { (day, mealDay) ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
                    border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {

                        CardHeader(day)

                        DayWiseMealsListing(mealDay.meals)

                        NutritionDonutChart(mealDay.nutrients)

                    }
                }
            }
        }
    }
}
