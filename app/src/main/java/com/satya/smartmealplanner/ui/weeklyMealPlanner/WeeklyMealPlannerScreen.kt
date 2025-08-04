package com.satya.smartmealplanner.ui.weeklyMealPlanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.weeklyMealPlan.MealDay
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer
import com.satya.smartmealplanner.utils.UIHelpers
import kotlinx.coroutines.flow.Flow

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
                WeeklyMealPlanScreenUI(weeklyPlanList)
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeeklyMealPlanScreenUI(week: List<Pair<String, MealDay>>) {
    LazyColumn {
        item {
            week.forEach { (day, mealDay) ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = day,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            )
                        }


                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            mealDay.meals.forEach { meal ->
                                val imageURL = "https://spoonacular.com/recipeImages/${meal.image}"

                                Card(
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(160.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.White)
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(imageURL),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp)
                                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = meal.title,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.Black,
                                            maxLines = 2,
                                            lineHeight = 16.sp,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp)
                                                .fillMaxWidth()
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Row(
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp)
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "${meal.readyInMinutes} min",
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                            Text(
                                                text = "${meal.servings} servings",
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }


                        val nutrients: List<Pair<String, Double>> = listOf(
                            "Protein" to mealDay.nutrients.protein,
                            "Fat" to mealDay.nutrients.fat,
                            "Carbohydrates" to mealDay.nutrients.carbohydrates,
                            "Calories" to mealDay.nutrients.calories
                        )


                        val maxValue = nutrients.maxOf { it.second }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Nutrient Breakdown", style = MaterialTheme.typography.titleLarge)

                            nutrients.forEach { (label, value) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = label, modifier = Modifier.weight(1f))
                                    Box(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .weight(1f)
                                            .background(Color.Gray.copy(alpha = 0.2f))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth(value.toFloat() / maxValue.toFloat())
                                                .background(MaterialTheme.colorScheme.primary)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = String.format("%.1f", value))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
