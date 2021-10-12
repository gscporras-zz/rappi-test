package com.rappi.android.ui.feature.detail.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.android.repository.MovieRepository
import com.rappi.android.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvDetailViewModel @Inject constructor(
    private val tvRepository: TvRepository
): ViewModel() {

    private val movieIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    val movieFlow = movieIdSharedFlow.flatMapLatest {
        tvRepository.loadTvById(it)
    }

    val videoListFlow = movieIdSharedFlow.flatMapLatest {
        tvRepository.loadTvVideoList(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    val castListFlow = movieIdSharedFlow.flatMapLatest {
        tvRepository.loadTvCastList(it)
    }

    init {
        Timber.d("Injection DetailViewModel")
    }

    fun fetchMovieDetailsById(id: Int) = movieIdSharedFlow.tryEmit(id)
}