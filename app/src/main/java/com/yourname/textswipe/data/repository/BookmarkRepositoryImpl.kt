package com.yourname.textswipe.data.repository

import com.yourname.textswipe.data.local.dao.BookmarkDao
import com.yourname.textswipe.domain.model.Bookmark
import com.yourname.textswipe.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.yourname.textswipe.data.mapper.toDomain
import com.yourname.textswipe.data.mapper.toEntity

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun getAllBookmarks(): Flow<List<Bookmark>> {
        return bookmarkDao.getAllBookmarks().map { list ->
            list.map { bookmark -> bookmark.toDomain() }
        }
    }

    override suspend fun getBookmarkByItemId(id: String): Bookmark? {
        return bookmarkDao.getBookmarkByItemId(id)?.toDomain()
    }

    override suspend fun upsertBookmarks(bookmarks: List<Bookmark>) {

        val entities = bookmarks.map {
            it.toEntity()
        }

        bookmarkDao.upsertBookmarks(entities)
    }

    override suspend fun deleteBookmarks(bookmarks: List<Bookmark>) {
        val entities = bookmarks.map {
            it.toEntity()
        }

        bookmarkDao.deleteBookmarks(entities)
    }

    override fun isBookmarked(itemId: String): Flow<Boolean> {
        return bookmarkDao.isBookmarked(itemId)
    }
}