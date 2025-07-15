package com.satya.smartmealplanner.ui.searchByNutrients.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.utils.Loader

@Composable
fun SearchByNutrientsScreen(
    navController: NavController, viewModel: RecipeViewModel = hiltViewModel()
) {

    var recipeByNutrientsState = viewModel.recipeByNutrientsState

    LaunchedEffect(Unit) {
        viewModel.getRecipeByNutrients(10, 100, 10, 100, 10, 100, 10, 100)
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.outline_arrow_back),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = { navController.popBackStack() }))

            Text(
                text = "Search By Nutrients",
                fontSize = 22.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            )
        }

        when {
            recipeByNutrientsState.isLoading -> Loader()

            recipeByNutrientsState.error != null -> Text(text = recipeByNutrientsState.error)

            recipeByNutrientsState.recipes == null || recipeByNutrientsState.recipes.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No recipes found, please select another option.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            else -> {
                Text(text = recipeByNutrientsState.recipes.toString())
            }

        }

    }
}