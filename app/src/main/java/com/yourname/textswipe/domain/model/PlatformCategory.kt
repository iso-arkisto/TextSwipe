package com.yourname.textswipe.domain.model

import java.util.UUID

data class PlatformCategory(
    val id: String = UUID.randomUUID().toString(),
    val platformId: String,
    val name: String,
    val description: String? = null,
    val isCustom: Boolean = false
)