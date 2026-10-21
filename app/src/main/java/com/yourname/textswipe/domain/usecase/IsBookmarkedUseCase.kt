package com.yourname.textswipe.domain.usecase

import com.yourname.textswipe.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsBookmarkedUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke(itemId: String): Flow<Boolean> {
        return repository.isBookmarked(itemId)
    }
}