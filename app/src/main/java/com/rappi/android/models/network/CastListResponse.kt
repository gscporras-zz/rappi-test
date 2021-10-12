package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.Cast
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.Video

@Immutable
data class CastListResponse(
    val id: Int,
    val results: List<Cast>
): NetworkResponseModel