package com.yourname.textswipe.presentation.bookmark

import com.yourname.textswipe.domain.model.FeedItem

fun FeedItem.toBookmarkTile(): BookmarkTile {
    return when (this) {
        is FeedItem.Text -> BookmarkTile(title, category.name, content)
        is FeedItem.Quote -> BookmarkTile(author.name, "Quotes", text)
        is FeedItem.ForumPost -> BookmarkTile(title, "Posts", text)
    }
}
