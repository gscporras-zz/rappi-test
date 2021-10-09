package com.rappi.android.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.android.data.model.MovieItem
import com.rappi.android.data.model.VideoItem
import com.rappi.android.data.model.VideoResponse
import com.rappi.android.data.network.MovieApiClient
import com.rappi.android.data.repository.MovieRepository
import com.rappi.android.utils.CustomResult
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiService = MovieApiClient.service
    private lateinit var repository: MovieRepository
    var popularMovies: List<MovieItem> by mutableStateOf(listOf())
    var topRatedMovies: List<MovieItem> by mutableStateOf(listOf())
    var movie: MovieItem by mutableStateOf(MovieItem())
    var moviesSearched: List<MovieItem> by mutableStateOf(listOf())
    var videos: List<VideoItem> by mutableStateOf(listOf())

    init {
        fetchPopularMovies()
        fetchTopRatedMovies()
    }

    fun fetchPopularMovies() {
        repository = MovieRepository(apiService)
        viewModelScope.launch {
            when (val response = repository.fetchPopularMovies()) {
                is CustomResult.OnSuccess -> {
                    Log.d("MainViewModel", "Success")
                    popularMovies = response.data.results
                }
                is CustomResult.OnError -> {
                    Log.d("MainViewModel", "FAILURE")
                }
                else -> {}
            }
        }
    }

    fun fetchTopRatedMovies() {
        repository = MovieRepository(apiService)
        viewModelScope.launch {
            when (val response = repository.fetchTopRatedMovies()) {
                is CustomResult.OnSuccess -> {
                    Log.d("MainViewModel", "Success")
                    topRatedMovies = response.data.results
                }
                is CustomResult.OnError -> {
                    Log.d("MainViewModel", "FAILURE")
                }
                else -> {}
            }
        }
    }

    fun fetchMovieDetail(movieId: Int?) {
        repository = MovieRepository(apiService)
        viewModelScope.launch {
            when (val response = repository.fetchMovieDetail(movieId)) {
                is CustomResult.OnSuccess -> {
                    Log.d("MainViewModel", "Success")
                    movie = response.data
                }
                is CustomResult.OnError -> {
                    Log.d("MainViewModel", "FAILURE")
                }
                else -> {}
            }
        }
    }

    fun fetchSearchMovies(query: String?) {
        repository = MovieRepository(apiService)
        viewModelScope.launch {
            when (val response = repository.fetchSearchMovies(query)) {
                is CustomResult.OnSuccess -> {
                    Log.d("MainViewModel", "Success")
                    moviesSearched = response.data.results
                }
                is CustomResult.OnError -> {
                    Log.d("MainViewModel", "FAILURE")
                }
                else -> {}
            }
        }
    }

    fun fetchVideosDetail(movieId: Int?) {
        repository = MovieRepository(apiService)
        viewModelScope.launch {
            when (val response = repository.fetchVideosDetail(movieId)) {
                is CustomResult.OnSuccess -> {
                    Log.d("MainViewModel", "Success")
                    videos = response.data.results
                }
                is CustomResult.OnError -> {
                    Log.d("MainViewModel", "FAILURE")
                }
                else -> {}
            }
        }
    }
}