package com.satya.smartmealplanner.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.presentation.preferences.SharedPreferencesViewModel
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.utils.UIHelpers
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
    val searchByQueryState = viewModel.searchByQueryState

    var showHorizontalViewPager by remember { mutableStateOf(false) }

    var preserveState by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!preserveState) {
            viewModel.fetchRecipesByQuery("")

            val currentDate: String = Utils.getCurrentDate()

            sharedPreferencesViewModel.getCurrentDate { previousDate: String? ->
                if (currentDate != previousDate) {
                    viewModel.getRandomRecipes(true)
                    viewModel.getRandomJoke(true)
                    viewModel.getRandomTrivia(true)
                    sharedPreferencesViewModel.saveCurrentDate(currentDate)
                } else {
                    viewModel.getRandomRecipes(false)
                    viewModel.getRandomTrivia(false)
                    viewModel.getRandomJoke(false)
                }
            }

            preserveState = true
        }
    }

    if (randomRecipes.isSuccess != null) {
        showHorizontalViewPager = true
    }

    LaunchedEffect(randomFoodTrivia, randomJokeState) {

        val list = baseCategoryList.toMutableList()

        randomJokeState.isSuccess?.let {
            list.add(
                2, DashboardCategory(
                    1001,
                    randomJokeState.isSuccess.text,
                    -1, "", "",
                )
            )
        }

        randomFoodTrivia.isSuccess?.let {
            list.add(
                6, DashboardCategory(
                    1002,
                    randomFoodTrivia.isSuccess.text,
                    -1, "", "",
                )
            )
        }

        updatedCategoryList = list
    }

    DashboardScreenUI(
        showHorizontalViewPager,
        updatedCategoryList,
        navController,
        randomRecipes,
        randomJokeState,
        randomFoodTrivia,
        searchByQueryState,
        onSearchQueryChanged = { query ->
            viewModel.fetchRecipesByQuery(query)
        }
    )

}