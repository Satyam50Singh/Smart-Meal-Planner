package com.satya.smartmealplanner.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.satya.smartmealplanner.R
import com.satya.smartmealplanner.data.model.dashboard.DashboardCategory
import com.satya.smartmealplanner.data.model.dashboard.FoodTrivia
import com.satya.smartmealplanner.data.model.dashboard.RandomJoke
import com.satya.smartmealplanner.data.model.randomRecipes.RandomRecipes
import com.satya.smartmealplanner.data.model.searchByQuery.SearchByQuery
import com.satya.smartmealplanner.presentation.navigation.Screen
import com.satya.smartmealplanner.presentation.search.State
import com.satya.smartmealplanner.ui.home.components.CategoryCard
import com.satya.smartmealplanner.ui.home.components.DietSwitchWithIcon
import com.satya.smartmealplanner.ui.home.components.FactCard
import com.satya.smartmealplanner.ui.home.components.HorizontalPagerWithIndicators
import com.satya.smartmealplanner.ui.utils.CircularLoader
import com.satya.smartmealplanner.utils.UIHelpers
import kotlinx.coroutines.delay

@Composable
fun DashboardScreenUI(
    showHorizontalViewPager: Boolean,
    updatedCategoryList: List<DashboardCategory>,
    navController: NavController,
    randomRecipes: State<RandomRecipes>,
    randomJokeState: State<RandomJoke>,
    randomFoodTrivia: State<FoodTrivia>,
    searchByQueryState: State<SearchByQuery>,
    onSearchQueryChanged: (String, Boolean) -> Unit,
    onReloadPage: (Boolean, Boolean) -> Unit
) {

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var errorMessageDialog by remember { mutableStateOf(false) }

    var isSwipeRefreshing by remember { mutableStateOf(false) }

    val isLoading =
        !isSwipeRefreshing && (randomRecipes.isLoading || randomJokeState.isLoading || randomFoodTrivia.isLoading || searchByQueryState.isLoading)

    if (errorMessage == null) {
        errorMessage = when {
            randomRecipes.isError != null -> randomRecipes.isError
            randomJokeState.isError != null -> randomJokeState.isError
            randomFoodTrivia.isError != null -> randomFoodTrivia.isError
            searchByQueryState.isError != null -> searchByQueryState.isError
            else -> null
        }
        errorMessageDialog = errorMessage != null
    }

    var searchQuery by remember { mutableStateOf("") }

    var isVeg by remember { mutableStateOf(true) }

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isSwipeRefreshing)

    LaunchedEffect(isSwipeRefreshing) {
        if (isSwipeRefreshing) {
            delay(1000)
            isSwipeRefreshing = false
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            onReloadPage(true, isVeg)
            isSwipeRefreshing = true
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }) {

            item {
                Text(
                    text = "Meal Planner",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            if (it.length >= 3) onSearchQueryChanged(searchQuery, isVeg)
                        },
                        label = { Text("Search recipes...") },
                        modifier = Modifier
                            .weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),  // replaces containerColor
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ),
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                Image(
                                    painter = painterResource(R.drawable.outline_close),
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        searchQuery = ""
                                    }
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.outline_mic),
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        UIHelpers.customToast(context, "Coming Soon")
                                    }
                                )
                            }
                        },
                        singleLine = true
                    )
                    DietSwitchWithIcon(onCheckedChange = {
                        isVeg = it
                        onSearchQueryChanged(searchQuery, it)
                    })
                }
            }

            item {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .heightIn(max = 1200.dp),
                    columns = GridCells.Fixed(3),
                    content = {
                        searchByQueryState.isSuccess?.results?.let { results ->
                            items(results) { recipe ->
                                Column(
                                    modifier = Modifier.clickable {
                                        navController.navigate(
                                            Screen.RecipeDetailById.createRoute(
                                                recipe.id.toString()
                                            )
                                        )
                                    }
                                ) {
                                    Card(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 4.dp
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(recipe.image),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .height(100.dp)
                                                .fillMaxWidth()
                                        )
                                    }

                                    Text(
                                        text = recipe.title,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp, vertical = 8.dp),
                                        fontSize = 12.sp,
                                        maxLines = 2,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }
                    })
            }

            item {
                Text(
                    text = "Explore",
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 16.dp)
                )
            }

            item {
                LazyHorizontalGrid(
                    modifier = Modifier.heightIn(max = 220.dp),
                    rows = GridCells.Fixed(1),
                    content = {
                        items(updatedCategoryList) { category ->
                            if (category.categoryId in listOf(2, 3, 4))
                                Column(
                                    modifier = Modifier
                                        .height(210.dp)
                                        .clickable {
                                            when (category.categoryRoute) {
                                                "search_by_ingredients" -> navController.navigate(
                                                    Screen.FindByIngredient.route
                                                )

                                                "search_by_cuisines" -> navController.navigate(
                                                    Screen.SearchByCuisines.route
                                                )

                                                "search_by_nutrients" -> navController.navigate(
                                                    Screen.SearchByNutrients.route
                                                )
                                            }
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Card(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 4.dp
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Image(
                                            painter = rememberAsyncImagePainter(category.categoryImage),
                                            contentDescription = null,
                                            contentScale = ContentScale.FillHeight,
                                            modifier = Modifier
                                                .height(160.dp)
                                                .width(170.dp)
                                        )
                                    }

                                    Text(
                                        text = category.categoryName,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(horizontal = 4.dp, vertical = 8.dp),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.W500,
                                        maxLines = 2,
                                        lineHeight = 14.sp
                                    )
                                }
                        }
                    })
            }

            item {
                if (showHorizontalViewPager) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .height(240.dp)
                    ) {
                        if (randomRecipes.isSuccess != null) {
                            val listOfRecipes = randomRecipes.isSuccess.recipes
                            HorizontalPagerWithIndicators(listOfRecipes, navController)
                        }
                    }
                }
            }

            item {
                if (updatedCategoryList.isNotEmpty()) {
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier.heightIn(max = 2000.dp),
                        columns = StaggeredGridCells.Fixed(2), content = {
                            items(updatedCategoryList, span = { item ->
                                if (item.categoryId in listOf(
                                        1001, 1002
                                    )
                                ) StaggeredGridItemSpan.FullLine else StaggeredGridItemSpan.SingleLane
                            }) { category ->
                                if (category.categoryId in listOf(1001, 1002)) {
                                    FactCard(
                                        category.categoryName,
                                        if (category.categoryId == 1001) "Joke" else "Fun Fact",
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxWidth(),

                                        )
                                } else if (category.categoryId !in listOf(2, 3, 4)) {
                                    CategoryCard(category, navController)
                                }
                            }
                        })
                }
            }
        }
    }

// Show loading indicator if isLoading is true
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.2f))
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            awaitPointerEvent()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            CircularLoader()
        }
    }

// Show Alert Dialog if errorMessage is not null
    if (errorMessageDialog) {
        AlertDialog(
            onDismissRequest = { errorMessageDialog = false },
            title = { Text(text = "Error") },
            text = { Text(text = errorMessage ?: "Something went wrong") },
            properties = DialogProperties(dismissOnClickOutside = false),
            confirmButton = {
                TextButton(onClick = {
                    errorMessageDialog = false
                }) {
                    Text("Okay")
                }
            }
        )
    }
}