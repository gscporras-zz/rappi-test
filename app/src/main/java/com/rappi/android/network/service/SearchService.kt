package com.rappi.android.network.service

import com.rappi.android.models.network.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {

    @GET("search/multi?language=en")
    suspend fun fetchSearchedMovie(@Query("query") query: String?, @Query("page") page: Int): ApiResponse<SearchResponse>

    @GET("person/{person_id}")
    suspend fun fetchDetailPerson(@Path("person_id") id: Int): ApiResponse<PersonResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchDetailMovie(@Path("movie_id") id: Int): ApiResponse<MovieResponse>

    @GET("tv/{tv_id}")
    suspend fun fetchDetailTv(@Path("tv_id") id: Int): ApiResponse<TvResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun fetchVideos(@Path("movie_id") id: Int): ApiResponse<VideoListResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun fetchCasts(@Path("movie_id") id: Int): ApiResponse<CastListResponse>
}