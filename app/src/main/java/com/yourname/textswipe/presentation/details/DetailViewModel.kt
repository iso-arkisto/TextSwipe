package com.yourname.textswipe.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yourname.textswipe.domain.usecase.GetItemByIdUseCase
import com.yourname.textswipe.domain.usecase.IsBookmarkedUseCase
import com.yourname.textswipe.domain.usecase.ToggleBookmarkUseCase
import com.yourname.textswipe.utils.DetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val isBookmarkedUseCase: IsBookmarkedUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    private val args = savedStateHandle.toRoute<DetailRoute>()
    private val currentItemId = args.itemId

    private val _uiEvent = Channel<DetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val isBookmarked: StateFlow<Boolean> = _uiState
        .flatMapLatest { isBookmarkedUseCase(currentItemId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        loadFeedItem()
    }

    fun toggleBookmark() {
        viewModelScope.launch {
            val result = toggleBookmarkUseCase(currentItemId)
            result.onFailure {
                val currentValue = isBookmarked.value

                if(currentValue) {
                    _uiEvent.send(DetailUiEvent.ShowSnackbar("Failed to remove bookmark"))
                } else {
                    _uiEvent.send(DetailUiEvent.ShowSnackbar("Failed to save bookmark"))
                }
            }
        }
    }

    private fun loadFeedItem() {
        viewModelScope.launch {
            val result = getItemByIdUseCase(currentItemId)

            _uiState.value = result.fold(
                onSuccess = { DetailUiState.Success(it) },
                onFailure = { DetailUiState.Error(it.localizedMessage) }
            )
        }
    }
}