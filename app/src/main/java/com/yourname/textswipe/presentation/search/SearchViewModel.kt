package com.yourname.textswipe.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.domain.usecase.GetBookmarksUseCase
import com.yourname.textswipe.domain.usecase.SearchItemsUseCase
import com.yourname.textswipe.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchItemsUseCase: SearchItemsUseCase,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    private val _uiEvent = Channel<SearchUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val bookmarkIds: StateFlow<List<String>> = getBookmarksUseCase()
        .map { items -> items.map { it.id } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _rawFoundItems = MutableStateFlow<List<FeedItem>>(emptyList())
    private val _searchError = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow<Boolean>(false)

    private val foundItems: StateFlow<List<SearchResult>> = combine(_rawFoundItems, bookmarkIds) { items, bookmarks ->
        items.map { item ->
            item.toSearchResult(isBookmarked = bookmarks.contains(item.id))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val uiState: StateFlow<SearchUiState> = combine(foundItems, _searchError, _isLoading) { items, error, isLoading ->
        if(error != null) {
            SearchUiState.Error(error)
        } else {
            if(isLoading) {
                SearchUiState.Loading
            } else {
                SearchUiState.Success(items)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchUiState.Loading
    )

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun searchItems() {
        viewModelScope.launch {
            val currentQuery = _searchQuery.value
            _isLoading.value = true

            searchItemsUseCase(currentQuery)
                .onSuccess { items ->
                    _rawFoundItems.value = items
                    _isLoading.value = false
                }
                .onFailure {
                    _searchError.value = "Failed to search items"
                    _isLoading.value = false
                }

        }
    }

    fun toggleBookmark(itemId: String) {
        viewModelScope.launch {
            toggleBookmarkUseCase(itemId).onFailure {
                val bookmarks = bookmarkIds.value
                if(bookmarks.contains(itemId)) {
                    _uiEvent.send(SearchUiEvent.ShowSnackbar("Failed to remove bookmark"))
                } else {
                    _uiEvent.send(SearchUiEvent.ShowSnackbar("Failed to save bookmark"))
                }
            }
        }
    }
}