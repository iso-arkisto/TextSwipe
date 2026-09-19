package com.yourname.textswipe.data.mapper

import com.yourname.textswipe.data.local.entity.BookmarkEntity
import com.yourname.textswipe.domain.model.Bookmark

fun Bookmark.toEntity(): BookmarkEntity {
    return BookmarkEntity(
        id = id,
        itemId = itemId,
        savedAt = savedAt
    )
}

fun BookmarkEntity.toDomain(): Bookmark {
    return Bookmark(
        id = id,
        itemId = itemId,
        savedAt = savedAt
    )
}