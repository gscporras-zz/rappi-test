package com.rappi.android.models

import androidx.compose.runtime.Immutable

@Immutable
data class Video(
    val id: Int,
    val name: String,
    val site: String,
    val key: String,
    val size: Int,
    val type: String
)