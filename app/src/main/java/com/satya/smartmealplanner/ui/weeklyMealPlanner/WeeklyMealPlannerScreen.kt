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
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer
import com.satya.smartmealplanner.utils.UIHelpers

@Composable
fun WeeklyMealPlannerScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    val weeklyMealPlanState = viewModel.weeklyMealPlanState

    LaunchedEffect(Unit) {
        viewModel.generateWeeklyMealPlan("week", 2000, "vegetarian", "eggs")
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
                    onClick = { UIHelpers.customToast(navController.context, "Coming soon") })
            )
        }

        when {
            weeklyMealPlanState.isLoading -> CircularLoader()

            weeklyMealPlanState.isError != null -> ErrorContainer(
                weeklyMealPlanState.isError ?: "Something went wrong!"
            )

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

                WeeklyMealPlannerScreenUI(weeklyPlanList)
            }
        }
    }
}

