package com.rappi.android.ui.feature.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.rappi.android.models.entities.Movie
import com.rappi.android.models.entities.Search
import com.rappi.android.models.network.NetworkState
import com.rappi.android.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    private val searchRepository: SearchRepository
): ViewModel() {

    private val _search = MutableStateFlow(null as String?)
    val search: StateFlow<String?> = _search

    private val _movies = MutableStateFlow(emptyList<Search>())
    val movies: StateFlow<List<Search>> = _movies

    private val _movieLoadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
    val movieLoadingState: State<NetworkState> get() = _movieLoadingState

    val moviePageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    fun search(term: String?) {
        _search.value = term
    }

    suspend fun searchMovie(query: String?) = moviePageStateFlow.flatMapLatest {
        _movieLoadingState.value = NetworkState.LOADING
        searchRepository.loadMovies(
            query = query,
            page = it,
            success = { _movieLoadingState.value = NetworkState.SUCCESS },
            error = { _movieLoadingState.value = NetworkState.ERROR }
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1).collectLatest {
        _movies.value = it
    }

    fun fetchNextMoviePage() {
        if (movieLoadingState.value != NetworkState.LOADING) {
            moviePageStateFlow.value++
        }
    }
}