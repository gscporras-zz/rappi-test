package com.rappi.android.ui.feature.detail.toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.android.repository.TopRatedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val topRatedRepository: TopRatedRepository
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    val movieFlow = movieIdSharedFlow.flatMapLatest {
        topRatedRepository.loadMovieById(it)
    }

    val videoListFlow = movieIdSharedFlow.flatMapLatest {
        topRatedRepository.loadVideoList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val castListFlow = movieIdSharedFlow.flatMapLatest {
        topRatedRepository.loadCastList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val keywordListFlow = movieIdSharedFlow.flatMapLatest {
        topRatedRepository.loadKeywordList(it)
    }

    val reviewListFlow = movieIdSharedFlow.flatMapLatest {
        topRatedRepository.loadReviewsList(it)
    }

    init {
        Timber.d("Injection TopRatedViewModel")
    }

    fun fetchMovieDetailsById(id: Int) = movieIdSharedFlow.tryEmit(id)
}