package com.yourname.textswipe.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.yourname.textswipe.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Delete
    suspend fun deleteBookmarks(bookmarks: List<BookmarkEntity>): Int

    @Upsert
    suspend fun upsertBookmarks(bookmarks: List<BookmarkEntity>)

    @Query("SELECT * FROM bookmarks WHERE item_id = :itemId")
    suspend fun getBookmarkByItemId(itemId: String): BookmarkEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE item_id = :itemId LIMIT 1)")
    fun isBookmarked(itemId: String): Flow<Boolean>
}