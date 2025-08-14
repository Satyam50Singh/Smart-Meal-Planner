package com.satya.smartmealplanner.ui.recipeDetailsById

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.presentation.viewmodel.RecipeViewModel
import com.satya.smartmealplanner.ui.recipeDetailsById.components.RecipeDetailCard
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.ui.utils.ErrorContainer

@Composable
fun RecipeDetailScreen(
    recipeId: Int?,
    navController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val selectedRecipeState = viewModel.selectedRecipeState
    val similarRecipesByIdState = viewModel.similarRecipesByIdState

    LaunchedEffect(Unit) {
        recipeId?.let {
            viewModel.getRecipeById(it)
            viewModel.fetchSimilarRecipesById(recipeId)
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
                text = selectedRecipeState.isSuccess?.title ?: "",
                fontSize = 22.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        when {
            selectedRecipeState.isLoading || similarRecipesByIdState.isLoading -> CircularLoader()

            selectedRecipeState.isError != null || similarRecipesByIdState.isError != null -> ErrorContainer(
                message = "Error: ${selectedRecipeState.isError}"
            )

            selectedRecipeState.isSuccess == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("No details found, Please try another recipe") }
            }

            else -> {
                val recipeDetails = selectedRecipeState.isSuccess
                val similarRecipes = similarRecipesByIdState.isSuccess
                if (similarRecipes != null) {
                    RecipeDetailCard(recipeDetails, similarRecipes, navController)
                }
            }
        }
    }
}