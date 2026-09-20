package com.yourname.textswipe.presentation.bookmark

import com.yourname.textswipe.domain.model.FeedItem

fun FeedItem.toBookmarkTile(): BookmarkTile {
    return when (this) {
        is FeedItem.Text -> BookmarkTile(title, category.name, content, id)
        is FeedItem.Quote -> BookmarkTile(author.name, "Quotes", text, id)
        is FeedItem.ForumPost -> BookmarkTile(title, "Posts", text, id)
    }
}
