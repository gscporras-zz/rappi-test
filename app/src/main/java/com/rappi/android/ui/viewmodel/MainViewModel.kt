package com.rappi.android.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.android.data.model.MovieItem
import com.rappi.android.data.network.MovieApiClient
import com.rappi.android.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val apiService = MovieApiClient.service
    private lateinit var repository: MovieRepository
    var popularMovies: List<MovieItem> by mutableStateOf(listOf())
    var topRatedMovies: List<MovieItem> by mutableStateOf(listOf())

    lateinit var clickedItem: MovieItem

    init {
        fetchPopularMovies()
        fetchTopRatedMovies()
    }

    private fun fetchPopularMovies() {
        repository = MovieRepository(apiService)
        viewModelScope.launch {
            when (val response = repository.fetchPopularMovies()) {
                is MovieRepository.Result.Success -> {
                    Log.d("MainViewModel", "Success")
                    popularMovies = response.movieList
                }
                is MovieRepository.Result.Failure -> {
                    Log.d("MainViewModel", "FAILURE")
                }
                else -> {}
            }
        }
    }

    private fun fetchTopRatedMovies() {
        repository = MovieRepository(apiService)
        viewModelScope.launch {
            when (val response = repository.fetchTopRatedMovies()) {
                is MovieRepository.Result.Success -> {
                    Log.d("MainViewModel", "Success")
                    topRatedMovies = response.movieList
                }
                is MovieRepository.Result.Failure -> {
                    Log.d("MainViewModel", "FAILURE")
                }
                else -> {}
            }
        }
    }

    fun itemClicked(item: MovieItem) {
        clickedItem = item
    }
}