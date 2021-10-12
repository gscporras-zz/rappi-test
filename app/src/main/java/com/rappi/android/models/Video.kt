package com.rappi.android.models

import androidx.compose.runtime.Immutable

@Immutable
data class Video(
    val id: String? = null,
    val name: String? = null,
    val site: String? = null,
    val key: String? = null,
    val size: Int? = null,
    val type: String? = null
)