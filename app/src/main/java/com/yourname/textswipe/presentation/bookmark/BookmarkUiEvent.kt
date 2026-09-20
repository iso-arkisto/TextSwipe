package com.yourname.textswipe.presentation.bookmark

sealed interface BookmarkUiEvent {
    data class ShowUndoShackbar(
        val itemId: String
    ) : BookmarkUiEvent

    data class Navigate(
        val route: Any
    ) : BookmarkUiEvent

    data class ShowSnackbar(
        val message: String
    ) : BookmarkUiEvent
}