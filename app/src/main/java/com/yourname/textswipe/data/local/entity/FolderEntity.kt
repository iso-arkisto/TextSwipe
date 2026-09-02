package com.yourname.textswipe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),

    val name: String,
    val description: String? = null,
    val createdAt: Long,
    val updatedAt: Long = System.currentTimeMillis(),
    val isSystem: Boolean = false
)