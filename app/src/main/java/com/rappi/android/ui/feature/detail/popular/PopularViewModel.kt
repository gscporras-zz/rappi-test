package com.rappi.android.ui.feature.detail.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.android.repository.PopularRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val popularRepository: PopularRepository
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    val movieFlow = movieIdSharedFlow.flatMapLatest {
        popularRepository.loadMovieById(it)
    }

    val videoListFlow = movieIdSharedFlow.flatMapLatest {
        popularRepository.loadVideoList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val castListFlow = movieIdSharedFlow.flatMapLatest {
        popularRepository.loadCastList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val keywordListFlow = movieIdSharedFlow.flatMapLatest {
        popularRepository.loadKeywordList(it)
    }

    val reviewListFlow = movieIdSharedFlow.flatMapLatest {
        popularRepository.loadReviewsList(it)
    }

    init {
        Timber.d("Injection MovieDetailViewModel")
    }

    fun fetchMovieDetailsById(id: Int) = movieIdSharedFlow.tryEmit(id)
}