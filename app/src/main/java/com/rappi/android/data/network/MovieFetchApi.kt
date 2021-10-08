package com.rappi.android.data.network

import com.rappi.android.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieFetchApi {
    @GET("movie/popular")
    suspend fun fetchPopularList(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ) : MovieResponse

    @GET("movie/top_rated")
    suspend fun fetchTopRatedList(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ) : MovieResponse
}