package com.satya.smartmealplanner.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

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