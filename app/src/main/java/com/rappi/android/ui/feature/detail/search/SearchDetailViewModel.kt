package com.rappi.android.ui.feature.detail.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.android.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    val movieFlow = movieIdSharedFlow.flatMapLatest {
        searchRepository.loadMovieById(it)
    }

    val videoListFlow = movieIdSharedFlow.flatMapLatest {
        searchRepository.loadVideoList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val castListFlow = movieIdSharedFlow.flatMapLatest {
        searchRepository.loadCastList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        Timber.d("Injection PopularViewModel")
    }

    fun fetchMovieDetailsById(id: Int) = movieIdSharedFlow.tryEmit(id)
}