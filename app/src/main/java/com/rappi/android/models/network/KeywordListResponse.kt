package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.Keyword
import com.rappi.android.models.NetworkResponseModel

@Immutable
data class KeywordListResponse(
    val id: Int,
    val keywords: List<Keyword>
) : NetworkResponseModel