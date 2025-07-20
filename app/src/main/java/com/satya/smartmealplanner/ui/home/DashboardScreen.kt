package com.satya.smartmealplanner.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.home.components.CategoryCard
import com.satya.smartmealplanner.ui.home.components.FactCard
import com.satya.smartmealplanner.ui.utils.Loader

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    val categoryList = viewModel.getCategoryList()
    val updatedCategoryList: MutableList<DashboardCategory> = mutableListOf()

    val randomJokeState = viewModel.randomJokeState
    val randomFoodTrivia = viewModel.foodTriviaState

    LaunchedEffect(Unit) {
        viewModel.getRandomJoke()
        viewModel.getRandomTrivia()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = "Categories . . .",
            fontSize = 24.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            fontWeight = FontWeight.Bold
        )

        when {
            randomJokeState.isLoading -> {
                Loader()
            }

            randomJokeState.error != null -> {
                Text(text = "Error: ${randomJokeState.error}")
            }


            randomJokeState.randomJoke != null -> {
                categoryList.forEachIndexed { index, category ->
                    if (index == 3) {
                        updatedCategoryList.add(
                            DashboardCategory(
                                1001,
                                randomJokeState.randomJoke.text,
                                -1, "", "",
                            )
                        )
                    } else {
                        updatedCategoryList.add(category)
                    }

                }

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    content = {
                        items(updatedCategoryList, span = { item ->
                            if (item.categoryId == 1001 || item.categoryId == 1005) StaggeredGridItemSpan.FullLine else StaggeredGridItemSpan.SingleLane
                        }) { category ->
                            if (category.categoryId == 1001) {
                                FactCard(
                                    category.categoryName,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) // Special card
                            } else {
                                CategoryCard(category, navController)
                            }
                        }
                    })
            }
        }
    }
}

