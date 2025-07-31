package com.satya.smartmealplanner.data.repository

import com.satya.smartmealplanner.data.local.dao.FoodFactDao
import com.satya.smartmealplanner.data.local.dao.RandomRecipeDao
import com.satya.smartmealplanner.data.local.entity.FoodFactEntity
import com.satya.smartmealplanner.data.local.entity.RandomRecipeEntity
import com.satya.smartmealplanner.data.model.autoCompleteIngredients.AutoCompleteIngredients
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.findByIngredients.FindByIngredientsResponse
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.randomRecipes.Recipe
import com.satya.smartmealplanner.data.model.randomRecipes.toEntity
import com.satya.smartmealplanner.data.model.recipeByCuisine.RecipeByCuisine
import com.satya.smartmealplanner.data.model.recipeByNutrients.RecipeByNutrients
import com.satya.smartmealplanner.data.model.recipeDetails.SelectedRecipeDetails
import com.satya.smartmealplanner.data.model.searchByQuery.SearchByQuery
import com.satya.smartmealplanner.data.model.similarRecipes.SimilarRecipesById
import com.satya.smartmealplanner.data.preferences.PreferenceKeys
import com.satya.smartmealplanner.data.preferences.SharedPreferencesManager
import com.satya.smartmealplanner.data.remote.ApiService
import com.satya.smartmealplanner.domain.model.Resource
import com.satya.smartmealplanner.domain.repository.RecipeRepository
import com.satya.smartmealplanner.utils.Constants
import com.satya.smartmealplanner.utils.Utils
import com.satya.smartmealplanner.utils.parseErrorBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val randomRecipeDao: RandomRecipeDao,
    private val foodFactDao: FoodFactDao,
    private val sharedPreferencesManager: SharedPreferencesManager
) : RecipeRepository {

    override suspend fun findByIngredients(
        ingredients: String,
        number: Int,
        apiKey: String
    ): FindByIngredientsResponse {
        return apiService.findByIngredients(ingredients, number, apiKey)
    }

    override suspend fun getRecipeDetailsById(recipeId: Int): SelectedRecipeDetails {
        return apiService.getRecipeDetailsById(recipeId)
    }

    override suspend fun getRecipeByCuisine(
        cuisine: String,
        diet: String
    ): RecipeByCuisine {
        return apiService.getRecipeByCuisine(cuisine, diet)
    }


    override suspend fun getRecipeByNutrients(
        minCarbs: Int,
        maxCarbs: Int,
        minProtein: Int,
        maxProtein: Int,
        minCalories: Int,
        maxCalories: Int,
        minFat: Int,
        maxFat: Int
    ): RecipeByNutrients {
        return apiService.findByNutrients(
            minCarbs,
            maxCarbs,
            minProtein,
            maxProtein,
            minCalories,
            maxCalories,
            minFat,
            maxFat
        )
    }

    override suspend fun getRandomJoke(): Resource<RandomJoke?> {
        val response = apiService.fetchRandomJoke()

        return when {
            response.isSuccessful -> {
                val body = response.body()
                if (body != null) {
                    val foodFactEntity = FoodFactEntity(
                        text = body.text,
                        type = Constants.JOKE
                    )

                    withContext(Dispatchers.IO) {
                        foodFactDao.deleteFoodFactByType("joke")
                        foodFactDao.insertFoodFact(foodFactEntity)
                    }
                }

                body?.let { Resource.Success(it) } ?: Resource.Error("Something went wrong")
            }

            response.errorBody() != null -> {
                parseErrorBody(response.errorBody(), response.code())
            }

            else -> {
                Resource.Error("Something went wrong")
            }
        }
    }

    override suspend fun getRandomJokeFromDb(type: String): Resource<FoodFactEntity> {
        val savedDate = sharedPreferencesManager.getString(PreferenceKeys.CURRENT_DATE, "")

        return if (savedDate.isNotEmpty()) {
            val savedJoke = foodFactDao.getFoodFactByType(Constants.JOKE)
            if (savedJoke != null) {
                Resource.Success(savedJoke)
            } else {
                Resource.Error("Something went wrong")
            }
        } else {
            val response = apiService.fetchRandomJoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val foodFactEntity = FoodFactEntity(
                        text = body.text,
                        type = Constants.JOKE
                    )

                    withContext(Dispatchers.IO) {
                        foodFactDao.deleteFoodFactByType("joke")
                        foodFactDao.insertFoodFact(foodFactEntity)
                    }

                    sharedPreferencesManager.putString(
                        PreferenceKeys.CURRENT_DATE,
                        Utils.getCurrentDate()
                    )
                    Resource.Success(foodFactEntity)
                } else {
                    Resource.Error("Something went wrong")
                }
            } else {
                parseErrorBody(errorBody = response.errorBody(), code = response.code())
            }
        }
    }

    override suspend fun getRandomTrivia(): Resource<FoodTrivia?> {
        val response = apiService.fetchRandomTrivia()

        return when {
            response.isSuccessful -> {
                val body = response.body()
                val foodFactEntity = FoodFactEntity(
                    text = body?.text ?: "",
                    type = Constants.TRIVIA
                )

                withContext(Dispatchers.IO) {
                    foodFactDao.deleteFoodFactByType("trivia")
                    foodFactDao.insertFoodFact(foodFactEntity)
                }

                body?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Something went wrong")
            }

            response.errorBody() != null -> {
                parseErrorBody(response.errorBody(), response.code())
            }

            else -> {
                Resource.Error("Something went wrong")
            }
        }
    }

    override suspend fun getRandomTriviaFromDb(type: String): Resource<FoodFactEntity> {
        return try {
            val savedDate = sharedPreferencesManager.getString(PreferenceKeys.CURRENT_DATE, "")

            if (savedDate.isNotEmpty()) {
                val savedTrivia = foodFactDao.getFoodFactByType(type)
                if (savedTrivia != null) {
                    Resource.Success(savedTrivia)
                } else {
                    Resource.Error("Something went wrong")
                }
            } else {
                val response = apiService.fetchRandomTrivia()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val foodFactEntity = FoodFactEntity(
                            text = body.text,
                            type = Constants.TRIVIA
                        )
                        withContext(Dispatchers.IO) {
                            foodFactDao.deleteFoodFactByType(Constants.TRIVIA)
                            foodFactDao.insertFoodFact(foodFactEntity)
                        }
                        sharedPreferencesManager.putString(
                            PreferenceKeys.CURRENT_DATE,
                            Utils.getCurrentDate()
                        )
                        Resource.Success(foodFactEntity)
                    } else {
                        Resource.Error("Something went wrong")
                    }
                } else {
                    parseErrorBody(errorBody = response.errorBody(), code = response.code())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unexpected error")
        }
    }

    override suspend fun fetchRandomRecipes(): Resource<RandomRecipes?> {
        val response = apiService.fetchRandomRecipes()

        return when {
            response.isSuccessful -> {
                val body = response.body()
                val recipeEntities: List<RandomRecipeEntity> =
                    body?.recipes?.map { recipe: Recipe -> recipe.toEntity() } ?: emptyList()

                withContext(Dispatchers.IO) {
                    randomRecipeDao.deleteAllRandomRecipes()
                    randomRecipeDao.insertRandomRecipes(recipeEntities)
                }

                body?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Something went wrong")

            }

            response.errorBody() != null -> {
                parseErrorBody(response.errorBody(), response.code())
            }

            else -> {
                Resource.Error("Something went wrong")
            }
        }
    }

    override suspend fun fetchRandomRecipesFromDb(): Resource<List<RandomRecipeEntity>> {
        return try {
            val savedDate = sharedPreferencesManager.getString(PreferenceKeys.CURRENT_DATE, "")

            if (savedDate.isNotEmpty()) {
                val savedRecipes = randomRecipeDao.getAllRandomRecipes()
                Resource.Success(savedRecipes)
            } else {
                val response = apiService.fetchRandomRecipes()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val recipeEntities = body.recipes.map { recipe -> recipe.toEntity() }
                        withContext(Dispatchers.IO) {
                            randomRecipeDao.deleteAllRandomRecipes()
                            randomRecipeDao.insertRandomRecipes(recipeEntities)
                        }
                        sharedPreferencesManager.putString(
                            PreferenceKeys.CURRENT_DATE,
                            Utils.getCurrentDate()
                        )
                        Resource.Success(recipeEntities)
                    } else {
                        Resource.Error("Something went wrong")
                    }
                } else {
                    parseErrorBody(response.errorBody(), response.code())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unexpected error")
        }
    }

    override suspend fun fetchRecipeByQuery(
        searchQuery: String,
        isVeg: Boolean
    ): Resource<SearchByQuery?> {
        val response = apiService.fetchRecipesByQuery(
            query = searchQuery,
            diet = if (isVeg) "vegetarian" else "Whole30"
        )
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Something went wrong!")
            }
        } else if (response.errorBody() != null) {
            parseErrorBody(response.errorBody(), response.code())
        } else {
            Resource.Error("Something went wrong!")
        }
    }

    override suspend fun fetchAutoCompleteIngredients(query: String): Resource<AutoCompleteIngredients?> {
        val response = apiService.fetchAutocompleteIngredients(query)

        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Something went wrong!")
            }
        } else if (response.errorBody() != null) {
            parseErrorBody(response.errorBody(), response.code())
        } else {
            Resource.Error("Something went wrong!")
        }
    }

    override suspend fun fetchSimilarRecipesById(recipeId: Int): Resource<SimilarRecipesById?> {
        val response = apiService.fetchSimilarRecipesById(recipeId)

        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Something went wrong!")
            }
        } else if (response.errorBody() != null) {
            parseErrorBody(response.errorBody(), response.code())
        } else {
            Resource.Error("Something went wrong!")
        }
    }
}

