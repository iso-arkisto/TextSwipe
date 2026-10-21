package com.yourname.textswipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yourname.textswipe.data.local.dao.BookmarkDao
import com.yourname.textswipe.data.local.entity.BookmarkEntity

@Database(
    entities = [BookmarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}