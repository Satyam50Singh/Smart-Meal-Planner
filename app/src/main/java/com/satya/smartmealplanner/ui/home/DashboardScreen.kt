package com.satya.smartmealplanner.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.home.components.CategoryCard

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    val categoryList = viewModel.getCategoryList()

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

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            content = {
                items(categoryList) { category ->
                    CategoryCard(category, navController)
                }
            })
    }


}