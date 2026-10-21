package com.yourname.textswipe.domain.usecase

import com.yourname.textswipe.domain.model.Bookmark
import com.yourname.textswipe.domain.repository.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository,
) {
    suspend operator fun invoke(bookmark: Bookmark): Result<Unit> {
        return try {
            repository.deleteBookmarks(listOf(bookmark))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}