package com.yourname.textswipe.presentation.search

sealed interface SearchUiEvent {
    data class ShowSnackbar(
        val message: String
    ) : SearchUiEvent
}