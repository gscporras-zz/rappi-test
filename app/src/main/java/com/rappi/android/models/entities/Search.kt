package com.rappi.android.models.entities

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import com.rappi.android.models.Cast
import com.rappi.android.models.Video

@Immutable
@Entity(primaryKeys = [("id")])
data class Search(
    var page: Int? = null,
    var videos: List<Video>? = listOf(),
    var casts: List<Cast>? = listOf(),
    val poster_path: String? = null,
    val profile_path: String? = null,
    val adult: Boolean? = false,
    val media_type: String? = null,
    val overview: String? = null,
    val release_date: String? = null,
    val genre_ids: List<Int>? = listOf(),
    val id: Int? = null,
    val original_title: String? = null,
    val original_language: String? = null,
    val title: String? = null,
    val name: String? = null,
    val backdrop_path: String? = null,
    val popularity: Float? = null,
    val vote_count: Int? = null,
    val video: Boolean? = null,
    val vote_average: Float? = null
)