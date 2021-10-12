package com.rappi.android.models.entities

import androidx.compose.runtime.Immutable
import androidx.room.Entity

@Immutable
@Entity(tableName = "person", primaryKeys = ["id"])
data class Person(
    var birthday: String? = null,
    var known_for_department: String? = null,
    val profile_path: String?,
    val adult: Boolean,
    val id: Int,
    val name: String,
    val biography: String,
    val place_of_birth: String,
    val popularity: Float
)