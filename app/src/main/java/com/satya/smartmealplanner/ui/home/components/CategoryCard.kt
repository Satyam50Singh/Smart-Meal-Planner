package com.satya.smartmealplanner.ui.home.components

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.presentation.navigation.Screen

@Composable
fun CategoryCard(category: DashboardCategory, navController: NavController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        onClick = {
            when (category.categoryRoute) {
                "search_by_ingredients" -> navController.navigate(Screen.FindByIngredient.route)
                "search_by_cuisines" -> navController.navigate(Screen.SearchByCuisines.route)
                else ->
                    Toast.makeText(context, "${category.categoryRoute} clicked", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(category.categoryImage),
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