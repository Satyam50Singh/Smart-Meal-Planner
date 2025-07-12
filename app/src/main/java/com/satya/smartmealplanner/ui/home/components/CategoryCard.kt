package com.satya.smartmealplanner.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory

@Composable
fun CategoryCard(category: DashboardCategory) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(category.categoryImage) ,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .height(90.dp)
                    .fillMaxWidth()
            )

            Text(
                category.categoryName,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                category.categoryDescription,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}