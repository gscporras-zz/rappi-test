package com.rappi.android.data.model

data class MovieItem(
    var backdrop_path: String? = null,
    var id: Int? = null,
    var original_title: String? = null,
    var overview: String? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    var title: String? = null,
    var vote_average: Double? = null,
    var genres: List<Genres?>? = null
)

data class Genres(
    var id: Int? = null,
    var name: String? = null
)