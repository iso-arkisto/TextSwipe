package com.yourname.textswipe.presentation.search

data class SearchResult(
    val title: String?,
    val category: String,
    val text: String,
    val itemId: String,
    val isBookmarked: Boolean
)