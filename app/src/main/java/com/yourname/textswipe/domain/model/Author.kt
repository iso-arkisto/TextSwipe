package com.yourname.textswipe.domain.model

import java.util.UUID

data class Author(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val imageUrl: String? = null,
    val bio: String? = null,
    val birthYear: Int,
    val deathYear: Int? = null
)