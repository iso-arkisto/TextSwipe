package com.yourname.textswipe.presentation.details

import com.yourname.textswipe.domain.model.FeedItem

sealed interface DetailUiState {
    data object Loading : DetailUiState

    data class Success(
        val feedItem: FeedItem
    ) : DetailUiState

    data class Error(
        val message: String?
    ) : DetailUiState
}