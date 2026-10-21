package com.yourname.textswipe.domain.usecase

import com.yourname.textswipe.domain.model.Bookmark
import com.yourname.textswipe.domain.repository.BookmarkRepository
import java.util.UUID
import javax.inject.Inject

class SaveBookmarkUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark): Result<Unit> {
        return try {
            repository.upsertBookmarks(listOf(bookmark))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend operator fun invoke(itemId: String): Result<Unit> {
        return try {
            repository.upsertBookmarks(listOf(
                Bookmark(
                    id = UUID.randomUUID().toString(),
                    itemId = itemId,
                    savedAt = System.currentTimeMillis()
                )
            ))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}