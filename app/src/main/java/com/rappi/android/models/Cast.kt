package com.rappi.android.models

import javax.annotation.concurrent.Immutable

@Immutable
data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String,
    val known_for_department: String
)