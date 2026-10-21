package com.yourname.textswipe.presentation.feed

sealed interface FeedUiEvent {
    data class ShowSnackbar(
        val message: String
    ) : FeedUiEvent
}