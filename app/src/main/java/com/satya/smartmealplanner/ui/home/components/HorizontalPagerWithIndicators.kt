package com.satya.smartmealplanner.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.randomRecipes.Recipe
import com.satya.smartmealplanner.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HorizontalPagerWithIndicators(listOfRecipes: List<Recipe>, navController: NavController) {

    val pagerState = rememberPagerState(pageCount = { listOfRecipes.size })
    val coroutineScope = rememberCoroutineScope()
    val autoScrollDelay = 5000L // 5 seconds

    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            Column {
                ImageCard(recipe = listOfRecipes[page], onClick = {
                    navController.navigate(Screen.RecipeDetailById.createRoute(listOfRecipes[page].id.toString()))
                })
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = listOfRecipes.size,
                    activeColor = MaterialTheme.colorScheme.primary,
                    inactiveColor = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 6.dp)
                        .clickable {
                            val currentPage = pagerState.currentPage
                            val totalPages = listOfRecipes.size
                            val nextPage = if (currentPage < totalPages - 1) currentPage + 1 else 0
                            coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                        }
                )
            }
        }

        LaunchedEffect(pagerState) {
            while (true) {
                delay(autoScrollDelay)
                coroutineScope.launch {
                    val currentPage = pagerState.currentPage
                    val totalPages = listOfRecipes.size
                    if (totalPages > 0) { // Ensure there are pages to scroll
                        val nextPage = if (currentPage < totalPages - 1) currentPage + 1 else 0
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }
    }
}
