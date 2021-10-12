package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.entities.Tv

@Immutable
data class DiscoverTvResponse(
    val page: Int,
    val results: List<Tv>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel