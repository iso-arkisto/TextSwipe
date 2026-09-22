package com.yourname.textswipe.utils

import kotlinx.serialization.Serializable

@Serializable
data class FeedRoute(val firstItemId: String? = null)

@Serializable
data object BookmarksRoute