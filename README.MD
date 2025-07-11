# 🍽️ Smart Meal Planner & Recipe Finder App

A food-based mobile app using Spoonacular APIs that helps users find recipes, plan meals, analyze nutrition, and manage pantry items.

---

## 🚀 Features Overview

| Feature                           | Spoonacular API Endpoint                     | Description                                                                 |
|-----------------------------------|----------------------------------------------|-----------------------------------------------------------------------------|
| 🔍 Recipe Search by Ingredients   | `/recipes/findByIngredients`                | Users select ingredients they have, and get relevant recipes.              |
| 📋 Recipe Details Page            | `/recipes/{id}/information`                 | Shows detailed cooking steps, nutrition, and ingredients.                  |
| 🎲 Random Recipe Discovery        | `/recipes/random`                           | "Surprise Me" button fetches a random recipe.                              |
| 🗓️ Weekly Meal Planner            | `/mealplanner/generate`                     | Generates a full week meal plan based on diet type (e.g., keto, vegan).    |
| 🌍 Search by Cuisine/Diet         | `/recipes/complexSearch`                    | Search recipes by keyword, cuisine, diet, or intolerances.                 |
| 🧪 Nutrition Analyzer             | `/recipes/parseIngredients` + `/nutritionWidget.json` | Analyze the nutrition of custom ingredients or recipes.           |
| 📦 Pantry Manager (Local)         | —                                            | Users add available ingredients; app recommends recipes accordingly.       |
| ❤️ Save Favorite Recipes          | — (Local or Firebase)                       | Save liked recipes for later.                                              |
| 🛒 Grocery List Generator         | —                                            | Auto-generate shopping list from selected recipes.                         |


---

## 📦 Setup Instructions

1. Get your API key from [Spoonacular](https://spoonacular.com/food-api).

---

## 📌 Notes

- Some endpoints have usage limits — cache frequently used responses.
- For personal projects, use the free API tier wisely.
- Add loading states and error handling for each network call.

---

