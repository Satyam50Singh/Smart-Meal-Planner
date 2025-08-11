package com.satya.smartmealplanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.satya.smartmealplanner.presentation.navigation.AppNavGraph
import com.satya.smartmealplanner.presentation.navigation.Screen
import com.satya.smartmealplanner.ui.theme.SmartMealPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartMealPlannerTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    }
                ) { paddingValues ->
                    AppNavGraph(
                        navController = navController,
                        innerPadding = paddingValues,
                        destination = Screen.Dashboard.route
                    )
                }
            }
        }
    }
}
