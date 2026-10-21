package com.yourname.textswipe.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yourname.textswipe.navigation.NavGraph
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import com.yourname.textswipe.navigation.BottomNavItem

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Feed,
        BottomNavItem.Bookmarks
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { item ->

                    val isSelected = currentDestination?.hasRoute(item.route::class) == true

                    NavigationBarItem(
                        icon = { Icon(
                            imageVector = item.icon,
                            contentDescription = null
                        ) },
                        label = { Text(item.title) },
                        selected = isSelected,
                        onClick = {
                            if(isSelected != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }

            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}