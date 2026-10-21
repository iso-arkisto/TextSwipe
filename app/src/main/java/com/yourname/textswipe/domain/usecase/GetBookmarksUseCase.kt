package com.yourname.textswipe.domain.usecase

import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.domain.repository.BookmarkRepository
import com.yourname.textswipe.domain.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBookmarksUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val feedRepository: FeedRepository
) {
    operator fun invoke(): Flow<List<FeedItem>> {
        return bookmarkRepository.getAllBookmarks().map { bookmarks ->
            val savedIds = bookmarks.map { it.itemId }.toSet()

            feedRepository.getFeedItems(null)
                .filter { item ->
                    savedIds.contains(item.id)
                }
        }
    }
}