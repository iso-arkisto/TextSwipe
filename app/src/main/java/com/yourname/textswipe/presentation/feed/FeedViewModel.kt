package com.yourname.textswipe.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.textswipe.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repositoryImpl: FeedRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<FeedUiState>(FeedUiState.Loading)
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        loadMoreItems()
    }

    fun onItemSwiped() {
        val currentState = _uiState.value

        if(currentState is FeedUiState.Success) {
            if(currentState.feedItems.isNotEmpty()) {
                val updatedFeed = currentState.feedItems.drop(1)
                _uiState.value = FeedUiState.Success(updatedFeed)
            }

            if(currentState.feedItems.size < 5) {
                loadMoreItems()
            }
        }
    }

    private fun loadMoreItems() {
        viewModelScope.launch {
            try {
                val newItems = repositoryImpl.getFeedItems()
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