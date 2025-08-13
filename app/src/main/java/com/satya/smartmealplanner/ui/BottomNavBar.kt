package com.satya.smartmealplanner.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.satya.smartmealplanner.presentation.navigation.Screen

@Composable
fun BottomNavBar(navController: NavHostController) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        val items = listOf(
            Screen.Dashboard,
            Screen.Favorites
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { it ->
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = it.icon),
                        contentDescription = it.title,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(text = it.title)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            )
        }
    }

}