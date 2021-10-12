package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.entities.TopRated

@Immutable
data class TopRatedResponse(
    val page: Int,
    val results: List<TopRated>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel