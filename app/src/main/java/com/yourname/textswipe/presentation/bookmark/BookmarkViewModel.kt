package com.yourname.textswipe.presentation.bookmark

import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.textswipe.domain.model.Bookmark
import com.yourname.textswipe.domain.repository.BookmarkRepository
import com.yourname.textswipe.domain.usecase.DeleteBookmarkUseCase
import com.yourname.textswipe.domain.usecase.GetBookmarksUseCase
import com.yourname.textswipe.domain.usecase.SaveBookmarkUseCase
import com.yourname.textswipe.utils.FeedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val saveBookmarkUseCase: SaveBookmarkUseCase
) : ViewModel() {
    val bookmarks = getBookmarksUseCase()
        .map { items ->
            items.map { it.toBookmarkTile() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiEvent = Channel<BookmarkUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val deletedBookmarksCache = ConcurrentHashMap<String, Bookmark>()

    fun deleteBookmarkRequested(itemId: String) {
        viewModelScope.launch {
            val result = removeBookmark(itemId)

            result
                .onSuccess {
                    _uiEvent.send(BookmarkUiEvent.ShowUndoShackbar(itemId))
                }
                .onFailure {
                    _uiEvent.send(BookmarkUiEvent.ShowSnackbar("Error: ${result.exceptionOrNull()?.message ?: "Unexpected error"}"))
                }
        }
    }

    fun onSnackbarAction(itemId: String, result: SnackbarResult) {
        viewModelScope.launch {
            when(result) {
                SnackbarResult.ActionPerformed -> {
                    restoreBookmark(itemId)
                }
                SnackbarResult.Dismissed -> {}
            }
        }
    }

    private fun restoreBookmark(itemId: String) {
        viewModelScope.launch {
            val itemToRestore = deletedBookmarksCache[itemId]

            if(itemToRestore != null) {
                val result = saveBookmarkUseCase(itemToRestore)

                result.onFailure {
                    _uiEvent.send(BookmarkUiEvent.ShowSnackbar("Failed to restore the bookmark"))
                }
            } else {
                _uiEvent.send(BookmarkUiEvent.ShowSnackbar("Failed to restore the bookmark"))
            }
        }
    }

    fun onBookmarkClicked(itemId: String) {
        viewModelScope.launch {
            _uiEvent.send(BookmarkUiEvent.Navigate(FeedRoute(itemId)))
        }
    }

    private suspend fun removeBookmark(itemId: String): Result<Unit> {
        val bookmark = bookmarkRepository.getBookmarkByItemId(itemId)

        return if(bookmark != null) {
            deletedBookmarksCache[itemId] = bookmark
            deleteBookmarkUseCase(bookmark)
        } else {
            Result.failure(Exception("Bookmark not found"))
        }
    }
}