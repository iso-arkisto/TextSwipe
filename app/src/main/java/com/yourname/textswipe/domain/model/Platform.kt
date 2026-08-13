package com.yourname.textswipe.domain.model

import java.util.UUID

data class Platform(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val imageUrl: String? = null,
    val colorHex: String? = null,
    val isCustom: Boolean = false
)