package com.yourname.textswipe.utils

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String?.toComposeColorOrNull(): Color? {
    if (this.isNullOrEmpty()) return null
    return runCatching {
        Color(this.toColorInt())
    }.getOrNull()
}