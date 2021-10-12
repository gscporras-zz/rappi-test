package com.rappi.android.models

import androidx.compose.runtime.Immutable

@Immutable
data class Review(
    val id: Int,
    val author: String,
    val content: String,
    val url: String
)
