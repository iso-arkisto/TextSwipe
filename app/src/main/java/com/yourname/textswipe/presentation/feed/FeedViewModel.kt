package com.yourname.textswipe.presentation.feed

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.yourname.textswipe.domain.repository.BookmarkRepository
import com.yourname.textswipe.domain.repository.FeedRepository
import com.yourname.textswipe.domain.usecase.DeleteBookmarkUseCase
import com.yourname.textswipe.domain.usecase.SaveBookmarkUseCase
import com.yourname.textswipe.utils.FeedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()
    val args = savedStateHandle.toRoute<FeedRoute>()

    private val _uiEvent = Channel<FeedUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val isBookmarked: StateFlow<Boolean> = _uiState
        .map { currentState ->
            if(currentState is FeedUiState.Success) {
                currentState.feedItems.firstOrNull()?.id
            } else {
                null
            }
        }
        .distinctUntilChanged()
        .flatMapLatest { itemId ->
            if(itemId != null) {
                bookmarkRepository.isBookmarked(itemId)
            } else {
                flowOf(false)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        loadMoreItems(args.firstItemId)
    }

    fun onItemSwiped() {
        val currentState = _uiState.value

        if(currentState is FeedUiState.Success) {
            if(currentState.feedItems.isNotEmpty()) {
                val updatedFeed = currentState.feedItems.drop(1)
                _uiState.value = FeedUiState.Success(updatedFeed)
            }

            if(currentState.feedItems.size < 5) {
                loadMoreItems(null)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toggleBookmark() {
        val isBookmarkedValue = isBookmarked.value
        val currentState = _uiState.value

        if(currentState is FeedUiState.Success) {

            currentState.feedItems.firstOrNull()?.let { item ->

                viewModelScope.launch {
                    if(isBookmarkedValue) {
                        val bookmark = bookmarkRepository.getBookmarkByItemId(item.id)
                        if(bookmark != null) {
                            val result = deleteBookmarkUseCase(bookmark)

                            result.onFailure {
                                _uiEvent.send(FeedUiEvent.ShowSnackbar("Failed to remove the bookmark"))
                            }
                        } else {
                            _uiEvent.send(FeedUiEvent.ShowSnackbar("Bookmark not found"))
                        }
                    } else {
                        val result = saveBookmarkUseCase(item.id)

                        result.onFailure {
                                _uiEvent.send(FeedUiEvent.ShowSnackbar("Failed to save the bookmark"))
                            }
                    }
                }
            }
        }
    }

    private fun loadMoreItems(firstItemId: String?) {
        viewModelScope.launch {
            try {
                val newItems = feedRepository.getFeedItems(firstItemId)
                val currentState = _uiState.value

                if(currentState is FeedUiState.Success) {
                    val combinedItems = currentState.feedItems + newItems
                    _uiState.value = FeedUiState.Success(combinedItems)
                } else {
                    _uiState.value = FeedUiState.Success(newItems)
                }
            } catch (e: Exception) {
                _uiState.value = FeedUiState.Error(e.localizedMessage)
            }
        }
    }
}