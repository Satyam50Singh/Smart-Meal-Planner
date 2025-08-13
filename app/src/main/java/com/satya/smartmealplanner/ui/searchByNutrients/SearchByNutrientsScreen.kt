package com.satya.smartmealplanner.ui.searchByNutrients

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.recipeByNutrients.NutrientRange
import com.satya.smartmealplanner.presentation.viewmodel.RecipeViewModel
import com.satya.smartmealplanner.ui.searchByNutrients.components.LaunchFilterDialog
import com.satya.smartmealplanner.ui.searchByNutrients.components.RecipeList
import com.satya.smartmealplanner.ui.searchByNutrients.components.SelectedNutrientsChips
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer

@Composable
fun SearchByNutrientsScreen(
    navController: NavController, viewModel: RecipeViewModel = hiltViewModel()
) {

    var recipeByNutrientsState = viewModel.recipeByNutrientsState

    var showDialog by rememberSaveable { mutableStateOf(true) }

    var nutrientRange by rememberSaveable(
        saver = Saver(
            save = { it },
            restore = { it }
        )) { mutableStateOf(NutrientRange()) }

    var showNutrientFilterChip by rememberSaveable { mutableStateOf(false) }

    var launchedFromUserAction by rememberSaveable { mutableStateOf(false) }

    if (showDialog) {
        LaunchFilterDialog(
            nutrientRange,
            onDismiss = {
                showDialog = false
                if (!launchedFromUserAction) {
                    navController.popBackStack()
                }
            },
            onApply = { range: NutrientRange, isChanged: Boolean ->
                showDialog = false
                nutrientRange = range
                showNutrientFilterChip = true

                if (isChanged || !launchedFromUserAction) {
                    viewModel.getRecipeByNutrients(
                        nutrientRange.carbs.start.toInt(),
                        nutrientRange.carbs.endInclusive.toInt(),
                        nutrientRange.protein.start.toInt(),
                        nutrientRange.protein.endInclusive.toInt(),
                        nutrientRange.calories.start.toInt(),
                        nutrientRange.calories.endInclusive.toInt(),
                        nutrientRange.fat.start.toInt(),
                        nutrientRange.fat.endInclusive.toInt()
                    )
                }

                launchedFromUserAction = true
            })
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
                text = "Search By Nutrients",
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        if (showNutrientFilterChip) {
            SelectedNutrientsChips(
                nutrientRange = nutrientRange,
                onClick = { showDialog = true }
            )
        }

        when {
            recipeByNutrientsState.isLoading -> CircularLoader()

            recipeByNutrientsState.isError != null -> ErrorContainer(
                recipeByNutrientsState.isError ?: "Something went wrong!"
            )

            recipeByNutrientsState.isSuccess == null || recipeByNutrientsState.isSuccess?.isEmpty() ?: true -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No recipes found, please select another option.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            else -> {
                recipeByNutrientsState.isSuccess?.let { recipes ->
                    RecipeList(navController, recipes)
                }
            }

        }

    }

}


