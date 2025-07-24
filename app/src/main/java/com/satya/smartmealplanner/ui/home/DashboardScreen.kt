package com.satya.smartmealplanner.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.presentation.preferences.SharedPreferencesViewModel
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.home.components.CategoryCard
import com.satya.smartmealplanner.ui.home.components.FactCard
import com.satya.smartmealplanner.ui.home.components.HorizontalPagerWithIndicators
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer
import com.satya.smartmealplanner.utils.Utils

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel(),
    sharedPreferencesViewModel: SharedPreferencesViewModel = hiltViewModel()
) {

    val baseCategoryList = remember { viewModel.getCategoryList() }
    var updatedCategoryList by remember { mutableStateOf<List<DashboardCategory>>(emptyList()) }

    val randomJokeState = viewModel.randomJokeState
    val randomFoodTrivia = viewModel.foodTriviaState
    val randomRecipes = viewModel.randomRecipesState

    var showHorizontalViewPager by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val currentDate: String = Utils.getCurrentDate()

        sharedPreferencesViewModel.getCurrentDate { date: String? ->
            if (currentDate == date) {
                viewModel.getRandomRecipes()
                viewModel.getRandomJoke()
                viewModel.getRandomTrivia()
                showHorizontalViewPager = true
                sharedPreferencesViewModel.saveCurrentDate(currentDate)
            } else {
                // fetch data from the room-db
            }
        }

    }

    LaunchedEffect(randomFoodTrivia, randomJokeState) {

        val list = baseCategoryList.toMutableList()

        randomJokeState.randomJoke?.let {
            list.add(
                2, DashboardCategory(
                    1001,
                    randomJokeState.randomJoke.text,
                    -1, "", "",
                )
            )
        }

        randomFoodTrivia.foodTrivia?.let {
            list.add(
                5, DashboardCategory(
                    1002,
                    randomFoodTrivia.foodTrivia.text,
                    -1, "", "",
                )
            )
        }

        updatedCategoryList = list
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
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp).fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )


        if (showHorizontalViewPager) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp).height(240.dp)
            ) {

                when {
                    randomRecipes.isLoading -> CircularLoader()

                    randomRecipes.isError != null -> ErrorContainer(randomRecipes.isError)

                    randomRecipes.isSuccess != null -> {
                        val listOfRecipes = randomRecipes.isSuccess.recipes
                        HorizontalPagerWithIndicators(listOfRecipes, navController)
                    }
                }
            }
        }

        when {
            randomJokeState.isLoading || randomFoodTrivia.isLoading -> CircularLoader()


            randomJokeState.error != null || randomFoodTrivia.error != null -> {
                ErrorContainer(
                    randomJokeState.error ?: randomFoodTrivia.error ?: "Something went wrong"
                )
            }

            updatedCategoryList.isNotEmpty() -> {
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
}

