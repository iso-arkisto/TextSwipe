package com.yourname.textswipe.presentation.search

sealed interface SearchUiState {
    data object Loading : SearchUiState

    data class Success(
        val foundItems: List<SearchResult>
    ) : SearchUiState

    data class Error(
        val message: String?
    ) : SearchUiState
}