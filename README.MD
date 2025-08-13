# 🍽️ Smart Meal Planner & Recipe Finder App

A food-based Android mobile app using Spoonacular APIs that helps users find recipes, plan meals, analyze nutrition, and manage pantry items.  
The app is built with **Clean Architecture, Hilt, Retrofit, Jetpack Compose**, and integrates **Firebase Crashlytics** for crash reporting.

---

## 🚀 Features Overview

| Feature                            | Spoonacular API Endpoint       | Description                                                                 |
|------------------------------------|--------------------------------|-----------------------------------------------------------------------------|
| 🔍 Recipe Search by Ingredients    | `/recipes/findByIngredients`   | Users select ingredients they have and get relevant recipes.               |
| 📋 Recipe Details Page             | `/recipes/{id}/information`    | Shows detailed cooking steps, nutrition, and ingredients.                  |
| 🎲 Random Recipe Discovery         | `/recipes/random`              | Fetches random recipes to display as a carousel in the dashboard.          |
| 🗓️ Weekly Meal Planner             | `/mealplanner/generate`        | Generates a meal plan based on user-defined diet/calories.                 |
| 🌍 Search by Cuisine/Diet          | `/recipes/complexSearch`       | Search recipes by keyword, cuisine, diet, or intolerances.                 |
| 🧪 Nutrition-Based Recipe Search   | `/recipes/findByNutrients`     | Filter recipes by carbs, protein, fat, and calories.                       |
| 😂 Random Joke                     | `/food/jokes/random`           | Display a random food joke for fun.                                        |
| 🤓 Random Food Trivia              | `/food/trivia/random`          | Display a random food fact/trivia.                                         |
| 🔠 Autocomplete Ingredient Search  | `/recipes/autocomplete`        | Suggest ingredients as users type in the search bar.                       |
| 🔁 Similar Recipes                 | `/recipes/{id}/similar`        | Display similar recipes based on a selected one.                           |
| 🔍 Search Recipes by Query         | `/recipes/complexSearch`       | Powers the dashboard search bar with keyword and optional diet filters.    |
| ❤️ Save Favorite Recipes           | — (Room/Firebase integration)  | Users can save their liked recipes offline or sync to cloud.               |
| 🛡️ Crash Reporting (Crashlytics)   | — (Firebase Crashlytics)       | Automatically reports app crashes and non-fatal issues for debugging.      |

---

## 🧩 Tech Stack

- **Architecture**: MVVM + Clean Architecture
- **Networking**: Retrofit + Kotlin Coroutines
- **Dependency Injection**: Hilt
- **Database**: Room DB + Firebase Firestore for favorites
- **UI**: Jetpack Compose
- **Build Tools**: Gradle, KSP
- **Crash Reporting**: Firebase Crashlytics

