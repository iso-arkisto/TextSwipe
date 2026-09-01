package com.yourname.textswipe.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "bookmarks",
    indices = [Index(value = ["item_id"], unique = true)]
)
data class BookmarkEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "item_id")
    val itemId: String,

    val folderId: String,
    val savedAt: Long = System.currentTimeMillis()
)