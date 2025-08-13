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
import com.satya.smartmealplanner.presentation.viewmodel.RecipeViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel(),
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
            viewModel.fetchRecipesByQuery("", isVeg = true, forceRefresh = false)
            viewModel.getRandomRecipes(false)
            viewModel.getRandomTrivia(false)
            viewModel.getRandomJoke(false)
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
                5, DashboardCategory(
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
        onSearchQueryChanged = { query, isVeg ->
            if (query.length < 3) {
                viewModel.fetchRecipesByQuery("", isVeg = isVeg, forceRefresh = false)
            } else {
                viewModel.onQueryChange(query, isVeg)
            }
        },
        onReloadPage = { forceRefresh, isVeg ->
            viewModel.getRandomRecipes(forceRefresh)
            viewModel.getRandomJoke(forceRefresh)
            viewModel.getRandomTrivia(forceRefresh)
            viewModel.fetchRecipesByQuery("", isVeg, forceRefresh)
        }
    )

}