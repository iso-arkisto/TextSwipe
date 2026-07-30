package com.yourname.textswipe.presentation.feed

import com.yourname.textswipe.domain.model.FeedItem

sealed interface FeedUiState {
    data object Loading : FeedUiState

    data class Success(
        val feedItems: List<FeedItem>
    ) : FeedUiState

    data class Error(
        val message: String
    ) : FeedUiState
}