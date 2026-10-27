package com.yourname.textswipe.presentation.search

import com.yourname.textswipe.domain.model.FeedItem

fun FeedItem.toSearchResult(isBookmarked: Boolean): SearchResult {
    return when(this) {
        is FeedItem.Text -> SearchResult(title, category.name, content, id, isBookmarked)
        is FeedItem.Quote -> SearchResult(author.name, "Quotes", text, id, isBookmarked)
        is FeedItem.ForumPost -> SearchResult(title, "Posts", text, id, isBookmarked)
    }
}