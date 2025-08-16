package com.satya.smartmealplanner.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satya.smartmealplanner.data.model.randomRecipes.Recipe
import com.satya.smartmealplanner.ui.utils.FetchImageFromUrl

@Composable
fun ImageCard(recipe: Recipe, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        FetchImageFromUrl(url = recipe.image, modifier = Modifier.fillMaxWidth())
        Text(
            text = recipe.title,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White,
            overflow = TextOverflow.Ellipsis
        )

    }
}
