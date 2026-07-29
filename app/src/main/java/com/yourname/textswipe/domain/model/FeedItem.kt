package com.yourname.textswipe.domain.model

import java.util.UUID

sealed interface FeedItem {
    val id: String

    data class Text(
        override val id: String = UUID.randomUUID().toString(),
        val category: String,
        val title: String,
        val content: String
    ) : FeedItem
}