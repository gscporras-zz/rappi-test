package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.Review

@Immutable
class ReviewListResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
) : NetworkResponseModel