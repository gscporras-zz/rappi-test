package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.entities.Search

@Immutable
data class SearchResponse(
    val page: Int,
    val results: List<Search>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel