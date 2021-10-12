package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.entities.Popular

@Immutable
data class PopularResponse(
    val page: Int,
    val results: List<Popular>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel