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
}