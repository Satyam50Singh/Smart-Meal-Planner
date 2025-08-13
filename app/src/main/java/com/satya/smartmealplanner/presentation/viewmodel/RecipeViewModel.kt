package com.satya.smartmealplanner.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satya.smartmealplanner.BuildConfig
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.model.searchByQuery.SearchByQuery
import com.satya.smartmealplanner.data.model.similarRecipes.SimilarRecipesById
import com.satya.smartmealplanner.data.model.weeklyMealPlan.WeeklyMealPlan
import com.satya.smartmealplanner.domain.model.Resource
import com.satya.smartmealplanner.domain.usecase.RecipeUseCase
import com.satya.smartmealplanner.presentation.handler.SearchQueryHandler
import com.satya.smartmealplanner.domain.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase
) : ViewModel() {

    var uiState by mutableStateOf(State<FindByIngredientsResponse>())
        private set

    fun findByIngredients(ingredients: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val recipe = recipeUseCase(ingredients, 20, BuildConfig.SPOONACULAR_API_KEY)
                uiState = uiState.copy(isSuccess = recipe, isLoading = false)
            } catch (e: Exception) {
                uiState = uiState.copy(isError = e.message, isLoading = false)
            }
        }
    }

    fun clearRecipes() {
        uiState = uiState.copy(isSuccess = FindByIngredientsResponse())
    }

    fun getCategoryList(): List<DashboardCategory> {
        return arrayListOf(
            DashboardCategory(
                2,
                "Search By Ingredients",
                R.drawable.search_by_ingredients,
                "Find recipes based on the ingredients you already have at home. Save time and reduce food waste!",
                "search_by_ingredients"
            ), DashboardCategory(
                3,
                "Search By Nutrients",
                R.drawable.search_by_nutrition,
                "Looking for high-protein, low-carb, or vitamin-rich meals? Get personalized recipes by nutritional content.",
                "search_by_nutrients"
            ), DashboardCategory(
                4,
                "Search By Cuisines",
                R.drawable.search_by_cuisine,
                "Explore diverse world cuisines – Italian, Mexican, Indian, Thai, and more. Find authentic meals by culture.",
                "search_by_cuisines"
            ), DashboardCategory(
                6,
                "Weakly Meal Planner",
                R.drawable.meal_planner,
                "Plan your breakfast, lunch, and dinner for the whole week with our weekly meal planner.",
                "weekly_meal_planner"
            )
        )
    }

    var selectedRecipeState by mutableStateOf(State<SelectedRecipeDetails>())
        private set

    fun getRecipeById(recipeId: Int) {

        viewModelScope.launch {
            selectedRecipeState = selectedRecipeState.copy(isLoading = true)

            try {
                val recipeDetails = recipeUseCase.getRecipeDetailsById(recipeId)
                selectedRecipeState =
                    selectedRecipeState.copy(isSuccess = recipeDetails, isLoading = false)
            } catch (e: Exception) {
                selectedRecipeState =
                    selectedRecipeState.copy(isError = e.message, isLoading = false)
            }
        }

    }

    var recipeByCuisineState by mutableStateOf(State<RecipeByCuisine>())
        private set

    fun getRecipeByCuisine(cuisine: String, diet: String) {
        viewModelScope.launch {
            recipeByCuisineState = recipeByCuisineState.copy(isLoading = true, isError = null)

            try {
                val listOfRecipes: RecipeByCuisine = recipeUseCase.getRecipeByCuisine(cuisine, diet)
                recipeByCuisineState = recipeByCuisineState.copy(
                    isLoading = false, isSuccess = listOfRecipes, isError = null
                )
            } catch (e: Exception) {
                recipeByCuisineState =
                    recipeByCuisineState.copy(isError = e.message, isLoading = false)
            }
        }
    }

    var recipeByNutrientsState by mutableStateOf(State<RecipeByNutrients>())
        private set

    fun getRecipeByNutrients(
        minCarbs: Int,
        maxCarbs: Int,
        minProtein: Int,
        maxProtein: Int,
        minCalories: Int,
        maxCalories: Int,
        minFat: Int,
        maxFat: Int
    ) {

        viewModelScope.launch {

            recipeByNutrientsState = recipeByNutrientsState.copy(isLoading = true, isError = null)

            try {
                val listOfRecipes: RecipeByNutrients = recipeUseCase.getRecipeByNutrients(
                    minCarbs,
                    maxCarbs,
                    minProtein,
                    maxProtein,
                    minCalories,
                    maxCalories,
                    minFat,
                    maxFat
                )
                recipeByNutrientsState = recipeByNutrientsState.copy(
                    isLoading = false, isSuccess = listOfRecipes, isError = null
                )
            } catch (e: Exception) {
                recipeByNutrientsState =
                    recipeByNutrientsState.copy(isError = e.message, isLoading = false)
            }
        }

    }

    var randomJokeState by mutableStateOf(State<RandomJoke>())
        private set

    fun getRandomJoke(forceRefresh: Boolean) {
        viewModelScope.launch {
            randomJokeState = randomJokeState.copy(isLoading = true, isError = null)

            try {
                val response = withContext(Dispatchers.IO) {
                    recipeUseCase.getRandomJoke(forceRefresh)
                }

                when (response) {
                    is Resource.Error -> {
                        randomJokeState =
                            randomJokeState.copy(isError = response.message, isLoading = false)
                    }

                    is Resource.Success -> {
                        response.data.let {
                            randomJokeState =
                                randomJokeState.copy(isSuccess = it, isLoading = false)
                        }
                    }
                }
            } catch (e: Exception) {
                randomJokeState = randomJokeState.copy(isError = e.message, isLoading = false)
                e.printStackTrace()
            }
        }
    }

    var foodTriviaState by mutableStateOf(State<FoodTrivia>())
        private set

    fun getRandomTrivia(forceRefresh: Boolean) {
        viewModelScope.launch {
            foodTriviaState = foodTriviaState.copy(isLoading = true, isError = null)

            try {
                val response = withContext(Dispatchers.IO) {
                    recipeUseCase.getRandomTrivia(forceRefresh)
                }

                when (response) {
                    is Resource.Error -> foodTriviaState =
                        foodTriviaState.copy(isLoading = false, isError = response.message)

                    is Resource.Success -> {
                        response.data.let {
                            foodTriviaState =
                                foodTriviaState.copy(
                                    isSuccess = it,
                                    isLoading = false,
                                    isError = null
                                )
                        }
                    }
                }
            } catch (e: Exception) {
                foodTriviaState = foodTriviaState.copy(isError = e.message, isLoading = false)
                e.printStackTrace()
            }
        }
    }

    var randomRecipesState by mutableStateOf(State<RandomRecipes>())
        private set

    fun getRandomRecipes(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            delay(300)

            randomRecipesState = randomRecipesState.copy(isLoading = true, isError = null)

            try {

                val response = withContext(Dispatchers.IO) {
                    recipeUseCase.getRandomRecipes(forceRefresh)
                }

                when (response) {
                    is Resource.Error -> {
                        randomRecipesState =
                            randomRecipesState.copy(isError = response.message, isLoading = false)
                    }

                    is Resource.Success -> {
                        response.data?.let {
                            randomRecipesState =
                                randomRecipesState.copy(isSuccess = it, isLoading = false)
                        }
                    }
                }

            } catch (e: Exception) {
                randomRecipesState = randomRecipesState.copy(isError = e.message, isLoading = false)
                e.printStackTrace()
            }
        }
    }

    private val searchQueryHandler = SearchQueryHandler(
        coroutineScope = viewModelScope,
        onSearchTriggered = { query, isVeg ->
            fetchRecipesByQuery(query, isVeg, false)
        },
        onSearchIngredientTriggered = { query ->
            fetchAutoCompleteIngredients(query)
        }
    )

    var searchByQueryState by mutableStateOf(State<SearchByQuery>())
        private set

    fun onQueryChange(query: String, isVeg: Boolean = true, isIngredientSearch: Boolean = false) {
        searchQueryHandler.onQueryChange(query, isVeg, isIngredientSearch)
    }

    fun fetchRecipesByQuery(searchQuery: String, isVeg: Boolean, forceRefresh: Boolean) {
        viewModelScope.launch {
            searchByQueryState = searchByQueryState.copy(isLoading = true, isError = null)

            try {
                val response = withContext(Dispatchers.IO) {
                    recipeUseCase.fetchRecipesByQuery(searchQuery, isVeg)
                }

                searchByQueryState = when (response) {
                    is Resource.Error -> searchByQueryState.copy(
                        isLoading = false,
                        isError = response.message
                    )

                    is Resource.Success -> {
                        searchByQueryState.copy(
                            isLoading = false,
                            isError = null,
                            isSuccess = response.data
                        )
                    }
                }
            } catch (e: Exception) {
                searchByQueryState = searchByQueryState.copy(
                    isLoading = false,
                    isError = e.message
                )
                e.printStackTrace()
            }
        }
    }

    var autoCompleteIngredientsState by mutableStateOf(State<List<String>>())
        private set

    private fun fetchAutoCompleteIngredients(query: String) {

        viewModelScope.launch {

            autoCompleteIngredientsState =
                autoCompleteIngredientsState.copy(isLoading = true, isError = null)

            try {

                val response = withContext(Dispatchers.IO) {
                    recipeUseCase.fetchAutoCompleteIngredients(query)
                }

                autoCompleteIngredientsState = when (response) {
                    is Resource.Error -> autoCompleteIngredientsState.copy(
                        isLoading = false,
                        isError = response.message
                    )

                    is Resource.Success -> autoCompleteIngredientsState.copy(
                        isLoading = false,
                        isError = null,
                        isSuccess = response.data
                    )
                }

            } catch (e: Exception) {
                autoCompleteIngredientsState =
                    autoCompleteIngredientsState.copy(isError = e.message, isLoading = false)
                e.printStackTrace()
            }
        }
    }


    var similarRecipesByIdState by mutableStateOf(State<SimilarRecipesById>())
        private set

    fun fetchSimilarRecipesById(recipeId: Int) {
        viewModelScope.launch {
            similarRecipesByIdState = similarRecipesByIdState.copy(isLoading = true, isError = null)

            try {
                val response = withContext(Dispatchers.IO) {
                    recipeUseCase.fetchSimilarRecipesById(recipeId)
                }

                similarRecipesByIdState = when (response) {
                    is Resource.Error -> similarRecipesByIdState.copy(isError = response.message, isLoading = false)

                    is Resource.Success -> similarRecipesByIdState.copy(isSuccess = response.data, isLoading = false)
                }
            } catch (e: Exception) {
                similarRecipesByIdState =
                    similarRecipesByIdState.copy(isError = e.message, isLoading = false)
                e.printStackTrace()
            }
        }
    }

    var weeklyMealPlanState by mutableStateOf(State<WeeklyMealPlan>())
        private set

    fun generateWeeklyMealPlan(
        loadApi: Boolean,
        timeFrame: String,
        targetCalories: Int,
        diet: String,
        exclude: String
    ) {
        viewModelScope.launch {
            weeklyMealPlanState = weeklyMealPlanState.copy(isLoading = true, isError = null)

            try {
                val response = withContext(Dispatchers.IO) {
                    recipeUseCase.generateWeeklyMealPlan(loadApi, timeFrame, targetCalories, diet, exclude)
                }

                weeklyMealPlanState = when(response) {
                    is Resource.Error ->
                        weeklyMealPlanState.copy(isError = response.message, isLoading = false)

                    is Resource.Success ->
                        weeklyMealPlanState.copy(isSuccess = response.data, isLoading = false)
                }
            } catch (e: Exception) {
                weeklyMealPlanState = weeklyMealPlanState.copy(isError = e.message, isLoading = false)
                e.printStackTrace()
            }
        }
    }

}
