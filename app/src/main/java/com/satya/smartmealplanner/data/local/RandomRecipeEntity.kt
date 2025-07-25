package com.satya.smartmealplanner.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.satya.smartmealplanner.data.model.randomRecipes.Recipe

@Entity(tableName = "random_recipes")
data class RandomRecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String
)

fun RandomRecipeEntity.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        image = imageUrl,
        summary = "",
        healthScore = 0.0,
        readyInMinutes = 0,
        aggregateLikes = 0,
        analyzedInstructions = emptyList(),
        cheap = false,
        cookingMinutes = 0,
        creditsText = "",
        cuisines = emptyList(),
        dairyFree = false,
        diets = emptyList(),
        dishTypes = emptyList(),
        extendedIngredients = emptyList(),
        gaps = "",
        glutenFree = false,
        license = "",
        instructions = "",
        lowFodmap = false,
        occasions = emptyList(),
        originalId = null,
        preparationMinutes = 0,
        pricePerServing = 0.0,
        servings = 0,
        sourceName = "",
        sourceUrl = "",
        spoonacularScore = 0.0,
        spoonacularSourceUrl = "",
        sustainable = false,
        vegan = false,
        vegetarian = false,
        veryHealthy = false,
        veryPopular = false,
        weightWatcherSmartPoints = 0,
        imageType = ""
    )
}
