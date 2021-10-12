package com.rappi.android.models.entities

import androidx.compose.runtime.Immutable
import androidx.room.Embedded
import androidx.room.Entity
import com.rappi.android.models.network.PersonDetail

@Immutable
@Entity(tableName = "People", primaryKeys = ["id"])
data class Person(
    var page: Int,
    @Embedded var personDetail: PersonDetail? = null,
    val profile_path: String?,
    val adult: Boolean,
    val id: Long,
    val name: String,
    val popularity: Float
)