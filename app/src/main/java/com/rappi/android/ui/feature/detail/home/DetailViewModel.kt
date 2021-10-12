package com.rappi.android.ui.feature.detail.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.android.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    val movieFlow = movieIdSharedFlow.flatMapLatest {
        movieRepository.loadMovieById(it)
    }

    val videoListFlow = movieIdSharedFlow.flatMapLatest {
        movieRepository.loadVideoList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val castListFlow = movieIdSharedFlow.flatMapLatest {
        movieRepository.loadCastList(it)
    }

    val keywordListFlow = movieIdSharedFlow.flatMapLatest {
        movieRepository.loadKeywordList(it)
    }

    val reviewListFlow = movieIdSharedFlow.flatMapLatest {
        movieRepository.loadReviewsList(it)
    }

    init {
        Timber.d("Injection DetailViewModel")
    }

    fun fetchMovieDetailsById(id: Int) = movieIdSharedFlow.tryEmit(id)
}