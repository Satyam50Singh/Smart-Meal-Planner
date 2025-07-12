package com.satya.smartmealplanner.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.satya.smartmealplanner.presentation.search.RecipeViewModel
import com.satya.smartmealplanner.ui.home.components.RecipeCard

@Composable
fun FindByIngredientScreen(
    navController: NavController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.findByIngredients("potatoes") // would replace it with random recipes api call
    }

    Log.w("TAG", "List of random recipes: ${uiState.recipes}")

    var input by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = input,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { input = it },
            label = { Text("Enter ingredients (comma separated)") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.findByIngredients(input) }) {
            Text("Search Recipes")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            uiState.error != null -> Text("Error: ${uiState.error}")

            uiState.recipes.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("No recipes found", modifier = Modifier.padding(16.dp), fontSize = 18.sp) }

            else -> LazyColumn {
                items(uiState.recipes) { recipe ->
                    if (recipe.missedIngredientCount < 4) {
                        RecipeCard(recipe)
                    }
                }
            }
        }
    }
}
