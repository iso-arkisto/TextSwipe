package com.yourname.textswipe.domain.model

import java.time.Instant

data class Bookmark(
    val id: String,
    val itemId: String,
    val savedAt: Instant
)