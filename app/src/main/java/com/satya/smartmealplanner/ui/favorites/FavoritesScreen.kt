package com.satya.smartmealplanner.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.presentation.viewmodel.FavoriteRecipeViewModel
import com.satya.smartmealplanner.ui.utils.CircularLoader

@Composable
fun Favorites(
    navController: NavHostController,
    favoriteRecipeViewModel: FavoriteRecipeViewModel = hiltViewModel()
) {


    val favoriteRecipeState = favoriteRecipeViewModel.favoriteRecipeState

    LaunchedEffect(Unit) {
        favoriteRecipeViewModel.getAllFavoriteRecipes()
    }

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Favorites",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontWeight = FontWeight.Bold
        )

        when {
            favoriteRecipeState.isLoading -> CircularLoader()

            favoriteRecipeState.isError != null -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                    ) {
                        Text(
                            text = favoriteRecipeState.isError,
                            modifier = Modifier
                                .padding(vertical = 56.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

            }

            favoriteRecipeState.isSuccess != null -> {
                LazyColumn(
                    modifier = Modifier.padding(bottom = 8.dp),
                ) {
                    items(favoriteRecipeState.isSuccess) { recipe ->
                        Card(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column {
                                Box {
                                    Image(
                                        painter = rememberAsyncImagePainter(recipe["recipeImage"].toString()),
                                        contentDescription = recipe["recipeTitle"].toString(),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 16.dp,
                                                    topEnd = 16.dp
                                                )
                                            ),
                                        contentScale = ContentScale.Crop
                                    )

                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_favorite_filled),
                                        contentDescription = "Remove from favorites",
                                        tint = Color.Red,
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(12.dp)
                                            .size(28.dp)
                                            .background(
                                                color = Color.White.copy(alpha = 0.8f),
                                                shape = CircleShape
                                            )
                                            .clickable {
                                                favoriteRecipeViewModel.deleteFavoriteRecipe(
                                                    (recipe["recipeId"] as Long).toInt()
                                                )
                                                favoriteRecipeViewModel.getAllFavoriteRecipes()
                                            }
                                            .padding(4.dp)
                                    )
                                }

                                Text(
                                    text = recipe["recipeTitle"].toString(),
                                    modifier = Modifier.padding(12.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 2.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Servings: ${recipe["recipeServings"]}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Ready in ${recipe["recipeReadyInMinutes"]} min",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                }
            }
        }
    }
}