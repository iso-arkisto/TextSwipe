package com.yourname.textswipe.domain.model

import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isCustom: Boolean = false
)