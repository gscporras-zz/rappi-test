package com.rappi.android.data.network

import com.rappi.android.data.model.MovieItem
import com.rappi.android.data.model.MovieResponse
import com.rappi.android.data.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieFetchApi {

    @GET("movie/upcoming")
    suspend fun fetchMovieLatest(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): MovieResponse

    @GET("movie/popular")
    suspend fun fetchPopularList(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun fetchTopRatedList(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun fetchMovieDetail(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): MovieItem

    @GET("search/movie")
    suspend fun fetchSearchMovies(
        @Query("query") query: String?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("include_adult") includeAdult: Boolean?
    ): MovieResponse

    @GET("movie/{movie_id}/videos")
    suspend fun fetchVideosDetail(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ): VideoResponse
}