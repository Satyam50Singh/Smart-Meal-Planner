package com.satya.smartmealplanner.ui.recipeDetailsById.components

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.model.similarRecipes.SimilarRecipesById
import com.satya.smartmealplanner.presentation.navigation.Screen

@Composable
fun RecipeDetailCard(
    recipe: SelectedRecipeDetails?,
    similarRecipes: SimilarRecipesById,
    navController: NavHostController
) {

    // Details
    LazyColumn(
        modifier = Modifier.padding(16.dp),
    ) {
        item {
            Image(
                painter = rememberAsyncImagePainter(recipe?.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = recipe?.title ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ready in ${recipe?.readyInMinutes} minutes • Serves ${recipe?.servings}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (!recipe?.summary.isNullOrEmpty()) {
                Text(
                    text = "Summary",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = Html.fromHtml(
                        recipe?.summary, Html.FROM_HTML_MODE_COMPACT
                    ).toString(), style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))
            }


            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        recipe?.extendedIngredients?.let {
            items(it) { ingredient ->
                Text(
                    text = "• ${ingredient.original}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Wine Pairing",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            if (!recipe?.winePairing?.pairedWines.isNullOrEmpty()) {
                Text(
                    text = recipe?.winePairing?.pairingText ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )

                recipe?.winePairing?.productMatches?.firstOrNull()?.let { wine ->
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = wine.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = wine.description, style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Text(
                    "No wine pairing info available.", style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {

                Text(
                    text = "Similar Recipes",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                if (similarRecipes.isEmpty()) {
                    Text(
                        "No Similar Recipes Found",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    )
                } else {
                    LazyRow(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.height(200.dp)
                    ) {
                        items(similarRecipes) { recipe ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .width(140.dp)
                                    .clickable {
                                        navController.navigate(
                                            Screen.RecipeDetailById.createRoute(
                                                recipeId = recipe.id.toString()
                                            )
                                        )
                                    }
                            ) {
                                Column {
                                    Image(
                                        painter = rememberAsyncImagePainter("https://spoonacular.com/recipeImages/${recipe.image}"),
                                        contentDescription = recipe.title,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp),
                                        contentScale = ContentScale.Crop
                                    )

                                    Text(
                                        text = recipe.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp, vertical = 8.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
