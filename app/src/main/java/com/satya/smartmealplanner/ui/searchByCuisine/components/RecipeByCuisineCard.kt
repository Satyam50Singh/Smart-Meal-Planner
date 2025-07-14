package com.satya.smartmealplanner.ui.searchByCuisine.components

import android.text.Html
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.satya.smartmealplanner.data.model.recipeByCuisine.Result
import com.satya.smartmealplanner.presentation.navigation.Screen


@Composable
fun RecipeByCuisineCard(recipe: Result, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp),
        onClick = { navController.navigate(Screen.RecipeDetailById.createRoute(recipe.id.toString())) }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(recipe.image),
                contentDescription = null,
                contentScale = ContentScale.Crop, // <-- important!
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp)) {
                Text(
                    recipe.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                if (recipe.summary.isNotEmpty()) {
                    Text(
                        text = Html.fromHtml(
                            recipe.summary,
                            Html.FROM_HTML_MODE_COMPACT
                        ).toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = Ellipsis
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
}