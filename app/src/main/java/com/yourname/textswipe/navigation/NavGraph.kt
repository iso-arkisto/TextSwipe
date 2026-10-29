package com.yourname.textswipe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yourname.textswipe.presentation.bookmark.BookmarkScreen
import com.yourname.textswipe.presentation.details.DetailScreen
import com.yourname.textswipe.presentation.feed.FeedScreen
import com.yourname.textswipe.presentation.search.SearchScreen
import com.yourname.textswipe.utils.BookmarksRoute
import com.yourname.textswipe.utils.DetailRoute
import com.yourname.textswipe.utils.FeedRoute
import com.yourname.textswipe.utils.SearchRoute

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
            FeedScreen(
                onSearchClick = { navController.navigate(SearchRoute) }
            )
        }
        composable<BookmarksRoute> {
            BookmarkScreen(
                onBookmarkClick = { navController.navigate(DetailRoute(it)) }
            )
        }
        composable<DetailRoute> {
            DetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable<SearchRoute> {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onItemClick = { navController.navigate(DetailRoute(it)) }
            )
        }
    }
}