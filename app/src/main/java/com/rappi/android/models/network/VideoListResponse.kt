package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.Video

@Immutable
data class VideoListResponse(
    val id: Int,
    val results: List<Video>
) : NetworkResponseModel