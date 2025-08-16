package com.satya.smartmealplanner.ui.weeklyMealPlanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.weeklyMealPlan.MealDay
import com.satya.smartmealplanner.presentation.viewmodel.RecipeViewModel
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer
import com.satya.smartmealplanner.ui.weeklyMealPlanner.components.CustomFilterBottomSheet
import com.satya.smartmealplanner.utils.Utils

@Composable
fun WeeklyMealPlannerScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    val weeklyMealPlanState = viewModel.weeklyMealPlanState

    var showFilterBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.generateWeeklyMealPlan(false, "week", 2000, "vegetarian", "eggs")
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.outline_arrow_back),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = { navController.popBackStack() })
            )

            Text(
                text = "Weekly Meal Planner",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .weight(1f),
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.outline_filter_alt),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = {
                        showFilterBottomSheet = true
                    }
                )
            )
        }

        when {
            weeklyMealPlanState.isLoading -> CircularLoader()

            weeklyMealPlanState.isError != null -> ErrorContainer(weeklyMealPlanState.isError)

            weeklyMealPlanState.isSuccess != null -> {
                val week = weeklyMealPlanState.isSuccess.week
                val weeklyPlanList: List<Pair<String, MealDay>> = listOf(
                    "Monday" to week.monday,
                    "Tuesday" to week.tuesday,
                    "Wednesday" to week.wednesday,
                    "Thursday" to week.thursday,
                    "Friday" to week.friday,
                    "Saturday" to week.saturday,
                    "Sunday" to week.sunday
                )

                val updatedWeeklyPlanList: List<Pair<String, MealDay>> =
                    weeklyPlanList.map { (day, mealDay) ->
                        if (day.equals(Utils.getCurrentDay(), ignoreCase = true)) {
                            "$day (Today)" to mealDay
                        } else {
                            day to mealDay
                        }
                    }

                WeeklyMealPlannerScreenUI(updatedWeeklyPlanList, navController)
            }
        }
    }

    if (showFilterBottomSheet) {
        CustomFilterBottomSheet(onClose = { isClosed, caloriesInput, excludeIngredientsInput ->
            showFilterBottomSheet = isClosed
            viewModel.generateWeeklyMealPlan(loadApi = true, targetCalories = Integer.parseInt(caloriesInput), exclude = excludeIngredientsInput, diet = "vegetarian", timeFrame = "week")
        })
    }
}
