package com.yourname.textswipe.presentation.details

sealed interface DetailUiEvent {
    data class ShowSnackbar(
        val message: String
    ) : DetailUiEvent
}