package com.yourname.textswipe.domain.usecase

import com.yourname.textswipe.domain.model.FeedItem
import com.yourname.textswipe.domain.repository.FeedRepository
import javax.inject.Inject

class SearchItemsUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(query: String): Result<List<FeedItem>> {
        return try {
            val items = repository.getFeedItems()
            val trimmedQuery = query.trim()

            if(trimmedQuery.isBlank()) return Result.success(emptyList())

            val filteredItems = items.filter { item ->
                when(item) {
                    is FeedItem.Text -> {
                        listOf(item.title, item.content, item.category.name).any { it?.contains(trimmedQuery, true) == true }
                    }
                is FeedItem.Quote -> {
                    listOf(item.text, item.author.name).any { it.contains(trimmedQuery, true) } ||
                            item.tags.any { it.contains(trimmedQuery, true) }
                }
                    is FeedItem.ForumPost -> {
                        item.tags.any { it.contains(trimmedQuery, true) } ||
                                listOf(item.category.name, item.platformCategory.name, item.title, item.text, item.author.username, item.platform.name).any { it?.contains(trimmedQuery, true) == true }
                    }
                }
            }.sortedBy { it.id }

            Result.success(filteredItems)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}