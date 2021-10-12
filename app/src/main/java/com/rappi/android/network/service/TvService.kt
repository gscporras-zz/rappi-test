package com.rappi.android.network.service

import com.rappi.android.models.network.CastListResponse
import com.rappi.android.models.network.MovieResponse
import com.rappi.android.models.network.TvResponse
import com.rappi.android.models.network.VideoListResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvService {

    @GET("tv/popular?language=en")
    suspend fun fetchTv(@Query("page") page: Int): ApiResponse<TvResponse>

    @GET("tv/{tv_id}/videos")
    suspend fun fetchTvVideos(@Path("tv_id") id: Int): ApiResponse<VideoListResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun fetchTvCasts(@Path("tv_id") id: Int): ApiResponse<CastListResponse>
}
