package com.yourname.textswipe.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.ui.graphics.vector.ImageVector
import com.yourname.textswipe.utils.BookmarksRoute
import com.yourname.textswipe.utils.FeedRoute

sealed class BottomNavItem<T>(
    val route: T,
    val title: String,
    val icon: ImageVector
) {
    data object Feed : BottomNavItem<FeedRoute>(
        route = FeedRoute(),
        title = "Feed",
        icon = Icons.Default.Newspaper
    )
    data object Bookmarks : BottomNavItem<BookmarksRoute>(
        route = BookmarksRoute,
        title = "Bookmarks",
        icon = Icons.Default.Bookmarks
    )
}