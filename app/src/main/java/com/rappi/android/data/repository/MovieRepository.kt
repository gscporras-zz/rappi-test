package com.rappi.android.data.repository

import android.util.Log
import com.rappi.android.BuildConfig.ApiKey
import com.rappi.android.data.model.MovieItem
import com.rappi.android.data.network.MovieFetchApi

class MovieRepository (private val movieFetchApi: MovieFetchApi) {

    sealed class Result {
        object LOADING : Result()
        data class Success(val movieList : List<MovieItem>) :Result()
        data class Failure(val throwable: Throwable): Result()
    }

    suspend fun fetchPopularMovies(): Result {
        return try {
            val movieList = movieFetchApi.fetchPopularList(ApiKey, "en-US", 1).results
            Log.d("MOVIELIST","success "+movieList.size)
            Result.Success(movieList = movieList)
        }catch (exception:Exception){
            Log.d("MOVIELIST","failure ")

            Result.Failure(exception)
        }
    }

    suspend fun fetchTopRatedMovies(): Result {
        return try {
            val movieList = movieFetchApi.fetchTopRatedList(ApiKey, "en-US", 1).results
            Log.d("MOVIELIST","success "+movieList.size)
            Result.Success(movieList = movieList)
        }catch (exception:Exception){
            Log.d("MOVIELIST","failure ")

            Result.Failure(exception)
        }
    }
}