package com.rappi.android.network.service

import com.rappi.android.models.entities.Movie
import com.rappi.android.models.network.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing?language=en")
    suspend fun fetchDiscoverMovie(@Query("page") page: Int): ApiResponse<MovieResponse>

    @GET("movie/popular?language=en")
    suspend fun fetchPopular(@Query("page") page: Int): ApiResponse<PopularResponse>

    @GET("movie/top_rated?language=en")
    suspend fun fetchTopRatedPeople(@Query("page") page: Int): ApiResponse<TopRatedResponse>

    @GET("movie/{movie_id}?language=en")
    suspend fun fetchMovieDetail(@Path("movie_id") id: Int): ApiResponse<Movie>

    @GET("movie/{movie_id}/videos")
    suspend fun fetchVideos(@Path("movie_id") id: Int): ApiResponse<VideoListResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun fetchCasts(@Path("movie_id") id: Int): ApiResponse<CastListResponse>

    suspend fun fetchKeywords(@Path("movie_id") id: Int): ApiResponse<KeywordListResponse>

    suspend fun fetchReviews(@Path("movie_id") id: Int): ApiResponse<ReviewListResponse>
}