package com.satya.smartmealplanner.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.satya.smartmealplanner.data.model.randomRecipes.Recipe


@Composable
fun HorizontalPagerWithIndicators(listOfRecipes: List<Recipe>, navController: NavController) {

    val pagerState = rememberPagerState(pageCount = { listOfRecipes.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(8.dp, bottom = 16.dp)) {
        HorizontalPager(state = pagerState) { page ->
            Column {
                ImageCard(recipe = listOfRecipes[page])
            }
        }
    }
}

@Composable
fun ImageCard(recipe: Recipe) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        FetchImageFromUrl(url = recipe.image)
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

@Composable
fun FetchImageFromUrl(url: String) {
    val painter: Painter = rememberAsyncImagePainter(url)

    Image(
        painter = painter,
        contentDescription = "Recipe Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}