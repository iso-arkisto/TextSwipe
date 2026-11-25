package com.yourname.textswipe.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.yourname.textswipe.R
import com.yourname.textswipe.utils.BookmarksRoute
import com.yourname.textswipe.utils.FeedRoute
import com.yourname.textswipe.utils.SettingsRoute

sealed class BottomNavItem<T>(
    val route: T,
    val titleRes: Int,
    val icon: ImageVector
) {
    data object Feed : BottomNavItem<FeedRoute>(
        route = FeedRoute(),
        titleRes = R.string.feed,
        icon = Icons.Default.Newspaper
    )

    data object Bookmarks : BottomNavItem<BookmarksRoute>(
        route = BookmarksRoute,
        titleRes = R.string.bookmarks,
        icon = Icons.Default.Bookmarks
    )

    data object Settings : BottomNavItem<SettingsRoute>(
        route = SettingsRoute,
        titleRes = R.string.settings,
        icon = Icons.Default.Settings
    )
}