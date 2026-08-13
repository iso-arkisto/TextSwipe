package com.yourname.textswipe.domain.model

import java.util.UUID

sealed interface FeedItem {
    val id: String

    data class Text(
        override val id: String = UUID.randomUUID().toString(),
        val category: Category,
        val title: String? = null,
        val content: String
    ) : FeedItem

    data class Quote(
        override val id: String = UUID.randomUUID().toString(),
        val text: String,
        val author: Author,
        val tags: List<String>
    ) : FeedItem

    data class ForumPost(
        override val id: String = UUID.randomUUID().toString(),
        val tags: List<String> = emptyList(),
        val category: Category,
        val platformCategory: PlatformCategory,
        val title: String? = null,
        val text: String,
        val author: ForumUser,
        val publishedAt: Long,
        val platform: Platform,
        val imageUrls: List<String> = emptyList(),
        val sources: List<SourceLink>
    ) : FeedItem
}