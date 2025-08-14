package com.satya.smartmealplanner.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.satya.smartmealplanner.R

@Composable
fun FetchImageFromUrl(url: String, modifier: Modifier) {
    val painter: Painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .build()
    )

    Image(
        painter = painter,
        contentDescription = "Recipe Image",
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}