package com.yourname.textswipe.domain.repository

import com.yourname.textswipe.domain.model.Bookmark
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getAllBookmarks(): Flow<List<Bookmark>>
    suspend fun getBookmarkByItemId(id: String): Bookmark?

    suspend fun upsertBookmarks(bookmarks: List<Bookmark>)

    suspend fun deleteBookmarks(bookmarks: List<Bookmark>)

    fun isBookmarked(itemId: String): Flow<Boolean>
}