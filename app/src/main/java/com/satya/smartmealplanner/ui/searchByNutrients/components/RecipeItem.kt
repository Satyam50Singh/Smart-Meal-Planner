package com.satya.smartmealplanner.ui.searchByNutrients.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrientsItem
import com.satya.smartmealplanner.presentation.navigation.Screen

@Composable
fun RecipeItem(navController: NavController, recipe: RecipeByNutrientsItem) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(180.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Recipe Image
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.image)
                        .error(R.drawable.placeholder)
                        .build()
                ),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            // Right Content
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = recipe.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        maxLines = 2
                    )
                    IconButton(
                        onClick = {
                            navController.navigate(
                                Screen.RecipeDetailById.createRoute(recipe.id.toString())
                            )
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrows_more_up),
                            contentDescription = "Details",
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Nutrient Info
                Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                    Text(text = "Carbs: ${recipe.carbs}", fontSize = 13.sp)
                    Text(text = "Protein: ${recipe.protein}", fontSize = 13.sp)
                    Text(text = "Calories: ${recipe.calories}", fontSize = 13.sp)
                    Text(text = "Fat: ${recipe.fat}", fontSize = 13.sp)
                }
            }
        }
    }
}
