package com.satya.smartmealplanner.ui.searchByCuisine

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.presentation.viewmodel.RecipeViewModel
import com.satya.smartmealplanner.ui.searchByCuisine.components.CuisinesFilterBottomSheet
import com.satya.smartmealplanner.ui.searchByCuisine.components.RecipeByCuisineCard
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer

@Composable
fun SearchByCuisineScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCuisine by rememberSaveable { mutableStateOf("Indian") }
    var selectedDiet by rememberSaveable { mutableStateOf("Vegetarian") }
    var hasLoadedOnce by rememberSaveable { mutableStateOf(false) }


    val recipeByCuisine = viewModel.recipeByCuisineState

    LaunchedEffect(Unit) {
        if (!hasLoadedOnce) {
            viewModel.getRecipeByCuisine(selectedCuisine, selectedDiet)
            hasLoadedOnce = true
        }
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.outline_arrow_back),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = { navController.popBackStack() }
                )
            )

            Text(
                text = "Search By Cuisine",
                fontSize = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = {
                    showBottomSheet = true
                },
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 0.dp)
            ) {
                Text(text = "Filter")
            }
        }

        when {
            recipeByCuisine.isLoading -> CircularLoader()

            recipeByCuisine.isError != null -> ErrorContainer(message = "Error: ${recipeByCuisine.isError}")

            recipeByCuisine.isSuccess == null -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { Text("No recipes found") }

            recipeByCuisine.isSuccess.results.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text("No recipes match your criteria.")
            }

            else -> {

                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Selected Filters:",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.W500
                    )

                    if (selectedCuisine.isNotEmpty()) {
                        FilterChip(
                            selected = true,
                            onClick = {
                                selectedCuisine = ""
                                viewModel.getRecipeByCuisine(selectedCuisine, selectedDiet)
                            },
                            label = { Text(selectedCuisine) },
                            trailingIcon = {
                                Image(
                                    painter = painterResource(R.drawable.outline_close_small),
                                    contentDescription = null
                                )
                            })
                    }
                    if (selectedDiet.isNotEmpty()) {
                        FilterChip(
                            selected = true,
                            onClick = {
                                selectedDiet = ""
                                viewModel.getRecipeByCuisine(selectedCuisine, selectedDiet)
                            },
                            label = { Text(selectedDiet) },
                            trailingIcon = {
                                Image(
                                    painter = painterResource(R.drawable.outline_close_small),
                                    contentDescription = null
                                )
                            })
                    }
                }

                LazyColumn {
                    items(recipeByCuisine.isSuccess.results) { recipe ->
                        RecipeByCuisineCard(recipe, navController)
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        CuisinesFilterBottomSheet(
            selectedCuisine,
            selectedDiet,
            onApplyClick = { cuisine, diet ->
                selectedCuisine = cuisine
                selectedDiet = diet
                showBottomSheet = false
                viewModel.getRecipeByCuisine(cuisine, diet)
            },
            onDismiss = {
                showBottomSheet = false
            }
        )
    }
}
