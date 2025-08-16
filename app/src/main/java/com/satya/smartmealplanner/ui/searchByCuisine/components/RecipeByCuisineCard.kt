package com.satya.smartmealplanner.ui.searchByCuisine.components

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.satya.smartmealplanner.data.model.recipeByCuisine.Result
import com.satya.smartmealplanner.presentation.navigation.Screen
import com.satya.smartmealplanner.ui.utils.FetchImageFromUrl


@Composable
fun RecipeByCuisineCard(recipe: Result, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
    ) {
        Column {
            FetchImageFromUrl(
                url = recipe.image,
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
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = "Read more...",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 18.sp,
                    textDecoration = TextDecoration.Underline,
                    color = if (isSystemInDarkTheme()) Color.Cyan else Color.Blue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            navController.navigate(Screen.RecipeDetailById.createRoute(recipe.id.toString()))
                        },
                    textAlign = TextAlign.End,
                )
            }
        }

    }
}