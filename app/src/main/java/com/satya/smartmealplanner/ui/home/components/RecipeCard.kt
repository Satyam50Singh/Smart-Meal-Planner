package com.satya.smartmealplanner.ui.home.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponseItem


@Composable
fun RecipeCard(recipe: FindByIngredientsResponseItem) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(recipe.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(recipe.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Used: ${recipe.usedIngredientCount}, Missed: ${recipe.missedIngredientCount}")
            }
        }
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = {
                    Toast.makeText(context, "Recipe clicked", Toast.LENGTH_SHORT).show()
                }) {
                Text("View Recipe")
            }

        }
    }
}
