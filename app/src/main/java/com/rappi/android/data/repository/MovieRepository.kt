package com.rappi.android.data.repository

import com.rappi.android.BuildConfig.ApiKey
import com.rappi.android.data.model.MovieItem
import com.rappi.android.data.model.MovieResponse
import com.rappi.android.data.model.VideoResponse
import com.rappi.android.data.network.MovieFetchApi
import com.rappi.android.utils.CustomError
import com.rappi.android.utils.CustomResult
import com.rappi.android.utils.LANGUAGE

class MovieRepository (private val movieFetchApi: MovieFetchApi) {

    suspend fun fetchPopularMovies(): CustomResult<MovieResponse> {
        return try {
            val movies = movieFetchApi.fetchPopularList(ApiKey, LANGUAGE, 1)
            CustomResult.OnSuccess(movies)
        } catch (ex: Exception) {
            CustomResult.OnError(CustomError(message = ex.message))
        }
    }

    suspend fun fetchTopRatedMovies(): CustomResult<MovieResponse> {
        return try {
            val movies = movieFetchApi.fetchTopRatedList(ApiKey, LANGUAGE, 1)
            CustomResult.OnSuccess(movies)
        } catch (ex: Exception) {
            CustomResult.OnError(CustomError(message = ex.message))
        }
    }

    suspend fun fetchMovieDetail(movieId: Int?): CustomResult<MovieItem> {
        return try {
            val movie = movieFetchApi.fetchMovieDetail(movieId, ApiKey, LANGUAGE)
            CustomResult.OnSuccess(movie)
        } catch (ex: Exception) {
            CustomResult.OnError(CustomError(message = ex.message))
        }
    }

    suspend fun fetchSearchMovies(query: String?): CustomResult<MovieResponse> {
        return try {
            val movies = movieFetchApi.fetchSearchMovies(query, ApiKey, LANGUAGE, false)
            CustomResult.OnSuccess(movies)
        } catch (ex: Exception) {
            CustomResult.OnError(CustomError(message = ex.message))
        }
    }

    suspend fun fetchVideosDetail(movieId: Int?): CustomResult<VideoResponse> {
        return try {
            val movies = movieFetchApi.fetchVideosDetail(movieId, ApiKey, LANGUAGE)
            CustomResult.OnSuccess(movies)
        } catch (ex: Exception) {
            CustomResult.OnError(CustomError(message = ex.message))
        }
    }
}