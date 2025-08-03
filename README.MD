# 🍽️ Smart Meal Planner & Recipe Finder App

A food-based Android mobile app using Spoonacular APIs that helps users find recipes, plan meals, analyze nutrition, and manage pantry items. The app is built with Clean Architecture, Hilt, Retrofit, and Jetpack Compose.

---

## 🚀 Features Overview

| Feature                            | Spoonacular API Endpoint                        | Description                                                                 | Status     |
|------------------------------------|--------------------------------------------------|-----------------------------------------------------------------------------|------------|
| 🔍 Recipe Search by Ingredients    | `/recipes/findByIngredients`                    | Users select ingredients they have and get relevant recipes.               | ✅ Done     |
| 📋 Recipe Details Page             | `/recipes/{id}/information`                     | Shows detailed cooking steps, nutrition, and ingredients.                  | ✅ Done     |
| 🎲 Random Recipe Discovery         | `/recipes/random`                               | "Surprise Me" button fetches random recipes.                               | ✅ Done     |
| 🗓️ Weekly Meal Planner             | `/mealplanner/generate`                         | Generates a meal plan based on user-defined diet/calories.                | 🛠️ Planned |
| 🌍 Search by Cuisine/Diet          | `/recipes/complexSearch`                        | Search recipes by keyword, cuisine, diet, or intolerances.                 | ✅ Done     |
| 🧪 Nutrition-Based Recipe Search   | `/recipes/findByNutrients`                      | Filter recipes by carbs, protein, fat, and calories.                       | ✅ Done     |
| 😂 Random Joke                     | `/food/jokes/random`                            | Display a random food joke for fun.                                        | ✅ Done     |
| 🤓 Random Food Trivia              | `/food/trivia/random`                           | Display a random food fact/trivia.                                         | ✅ Done     |
| 🔠 Autocomplete Ingredient Search  | `/recipes/autocomplete`                         | Suggest ingredients as users type in the search bar.                       | ✅ Done     |
| 🧂 Recipe Taste Profile            | `/recipes/{id}/tasteWidget.json`                | Analyze the taste profile of a recipe (sweetness, saltiness, etc.).       | 🛠️ Planned |
| 🔁 Similar Recipes                 | `/recipes/{id}/similar`                         | Display similar recipes based on a selected one.                           | ✅ Done     |
| 🔍 Search Recipes by Query         | `/recipes/complexSearch`                        | Basic keyword search with optional diet filters.                           | ✅ Done     |
| ❤️ Save Favorite Recipes           | — (Room/Firebase integration)                   | Users can save their liked recipes offline or sync to cloud.              | 🛠️ Planned |

---

## 🧩 Tech Stack

- **Architecture**: MVVM + Clean Architecture
- **Networking**: Retrofit + Kotlin Coroutines
- **DI**: Hilt
- **Database**: Room (for favorites/cache)
- **UI**: Jetpack Compose
- **Build Tools**: Gradle, KSP

---

## 📦 Setup Instructions

1. Clone the repository.
2. Get your API key from [Spoonacular](https://spoonacular.com/food-api).
3. Add your key in your `local.properties` or directly in `BuildConfig`:
   ```properties
   SPOONACULAR_API_KEY=your_api_key_here
