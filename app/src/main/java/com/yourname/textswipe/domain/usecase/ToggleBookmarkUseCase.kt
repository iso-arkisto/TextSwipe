package com.yourname.textswipe.domain.usecase

import com.yourname.textswipe.domain.repository.BookmarkRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository,
    private val saveBookmarkUseCase: SaveBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
) {
    suspend operator fun invoke(itemId: String): Result<Unit> {
        return try {
            val isBookmarked = repository.isBookmarkedOneShot(itemId)

            if(isBookmarked) {
                val bookmark = repository.getBookmarkByItemId(itemId)

                if(bookmark != null) {
                    deleteBookmarkUseCase(bookmark)
                } else {
                    throw NoSuchElementException("Bookmark not found")
                }
            } else {
                saveBookmarkUseCase(itemId)
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}