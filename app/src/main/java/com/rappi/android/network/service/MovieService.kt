package com.rappi.android.network.service

import com.rappi.android.models.network.CastListResponse
import com.rappi.android.models.network.KeywordListResponse
import com.rappi.android.models.network.ReviewListResponse
import com.rappi.android.models.network.VideoListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    suspend fun fetchKeywords(@Path("movie_id") id: Int): ApiResponse<KeywordListResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun fetchVideos(@Path("movie_id") id: Int): ApiResponse<VideoListResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun fetchCasts(@Path("movie_id") id: Int): ApiResponse<CastListResponse>

    suspend fun fetchReviews(@Path("movie_id") id: Int): ApiResponse<ReviewListResponse>
}