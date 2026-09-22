package com.yourname.textswipe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yourname.textswipe.presentation.bookmark.BookmarkScreen
import com.yourname.textswipe.presentation.feed.FeedScreen
import com.yourname.textswipe.utils.BookmarksRoute
import com.yourname.textswipe.utils.FeedRoute

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = FeedRoute(),
        modifier = modifier
    ) {
        composable<FeedRoute> {
            FeedScreen()
        }
        composable<BookmarksRoute> {
            BookmarkScreen(
                onNavigate = { navController.navigate(it) }
            )
        }
    }
}