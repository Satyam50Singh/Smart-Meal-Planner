package com.satya.smartmealplanner.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.presentation.preferences.SharedPreferencesViewModel
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
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

        sharedPreferencesViewModel.getCurrentDate { previousDate: String? ->
            if (currentDate != previousDate) {
                viewModel.getRandomRecipes(true)
                viewModel.getRandomJoke()
                viewModel.getRandomTrivia(true)
                sharedPreferencesViewModel.saveCurrentDate(currentDate)
            } else {
                viewModel.getRandomRecipes(false)
                viewModel.getRandomTrivia(false)
            }
        }

    }

    if (randomRecipes.isSuccess != null) {
        showHorizontalViewPager = true
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

        randomFoodTrivia.isSuccess?.let {
            list.add(
                5, DashboardCategory(
                    1002,
                    randomFoodTrivia.isSuccess.text,
                    -1, "", "",
                )
            )
        }

        updatedCategoryList = list
    }

    DashboardScreenUI(showHorizontalViewPager, updatedCategoryList, navController, randomRecipes, randomJokeState, randomFoodTrivia)

}