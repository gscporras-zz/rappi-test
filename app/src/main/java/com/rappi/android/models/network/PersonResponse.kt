package com.rappi.android.models.network

import androidx.compose.runtime.Immutable
import com.rappi.android.models.NetworkResponseModel
import com.rappi.android.models.entities.Person

@Immutable
data class PersonResponse(
    val page: Int,
    val results: List<Person>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel