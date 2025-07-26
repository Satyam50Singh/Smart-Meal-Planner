package com.satya.smartmealplanner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.presentation.search.State
import com.satya.smartmealplanner.ui.home.components.CategoryCard
import com.satya.smartmealplanner.ui.home.components.FactCard
import com.satya.smartmealplanner.ui.home.components.HorizontalPagerWithIndicators
import com.satya.smartmealplanner.ui.utils.CircularLoader

@Composable
fun DashboardScreenUI(
    showHorizontalViewPager: Boolean,
    updatedCategoryList: List<DashboardCategory>,
    navController: NavController,
    randomRecipes: State<RandomRecipes>,
    randomJokeState: State<RandomJoke>,
    randomFoodTrivia: State<FoodTrivia>
) {

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorMessageDialog by remember { mutableStateOf(false) }

    val isLoading =
        randomRecipes.isLoading || randomJokeState.isLoading || randomFoodTrivia.isLoading

    if (errorMessage == null) {
        errorMessage = when {
            randomRecipes.isError != null -> randomRecipes.isError
            randomJokeState.isError != null -> randomJokeState.isError
            randomFoodTrivia.isError != null -> randomFoodTrivia.isError
            else -> null
        }
        errorMessageDialog = errorMessage != null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Meal Planner",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Column {
            if (showHorizontalViewPager) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(240.dp)
                ) {
                    if (randomRecipes.isSuccess != null) {
                        val listOfRecipes = randomRecipes.isSuccess.recipes
                        HorizontalPagerWithIndicators(listOfRecipes, navController)
                    }
                }
            }

            if (updatedCategoryList.isNotEmpty()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2), content = {
                        items(updatedCategoryList, span = { item ->
                            if (item.categoryId in listOf(
                                    1001, 1002
                                )
                            ) StaggeredGridItemSpan.FullLine else StaggeredGridItemSpan.SingleLane
                        }) { category ->
                            if (category.categoryId in listOf(1001, 1002)) {
                                FactCard(
                                    category.categoryName,
                                    if (category.categoryId == 1001) "Joke" else "Fun Fact",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),

                                    )
                            } else {
                                CategoryCard(category, navController)
                            }
                        }
                    })
            }
        }
    }


    // Show loading indicator if isLoading is true
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.2f))
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            CircularLoader()
        }
    }

    // Show Alert Dialog if errorMessage is not null
    if (errorMessageDialog) {
        AlertDialog(
            onDismissRequest = { errorMessageDialog = false },
            title = { Text(text = "Error") },
            text = { Text(text = errorMessage ?: "Something went wrong") },
            properties = DialogProperties(dismissOnClickOutside = false),
            confirmButton = {
                TextButton(onClick = {
                    errorMessageDialog = false
                }) {
                    Text("Okay")
                }
            }
        )
    }
}