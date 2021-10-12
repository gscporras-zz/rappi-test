package com.rappi.android.ui.feature.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.rappi.android.models.entities.Popular
import com.rappi.android.models.entities.TopRated
import com.rappi.android.models.entities.Tv
import com.rappi.android.models.network.NetworkState
import com.rappi.android.repository.MovieRepository
import com.rappi.android.repository.PopularRepository
import com.rappi.android.repository.TopRatedRepository
import com.rappi.android.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val imageLoader: ImageLoader,
    private val tvRepository: TvRepository,
    private val movieRepository: MovieRepository,
    private val popularRepository: PopularRepository,
    private val topRatedRepository: TopRatedRepository
): ViewModel() {

    private val _selectedTab: MutableState<MainScreenHomeTab> = mutableStateOf(MainScreenHomeTab.TV)
    val selectedTab: State<MainScreenHomeTab> get() = _selectedTab

    //Tv
    private val _tvLoadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
    val tvLoadingState: State<NetworkState> get() = _tvLoadingState

    val tvs: State<MutableList<Tv>> = mutableStateOf(mutableListOf())
    val tvPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
    private val newTvFlow = tvPageStateFlow.flatMapLatest {
        _tvLoadingState.value = NetworkState.LOADING
        tvRepository.loadTvs(
            page = it,
            success = { _tvLoadingState.value = NetworkState.SUCCESS },
            error = { _tvLoadingState.value = NetworkState.ERROR }
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    //Popular
    private val _popularLoadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
    val popularLoadingState: State<NetworkState> get() = _popularLoadingState

    val populars: State<MutableList<Popular>> = mutableStateOf(mutableListOf())
    val popularPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
    private val newPopularFlow = popularPageStateFlow.flatMapLatest {
        _popularLoadingState.value = NetworkState.LOADING
        popularRepository.loadPopular(
            page = it,
            success = { _popularLoadingState.value = NetworkState.SUCCESS },
            error = { _popularLoadingState.value = NetworkState.ERROR }
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    //Top Rated
    private val _topRatedLoadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
    val topRatedLoadingState: State<NetworkState> get() = _topRatedLoadingState

    val topRateds: State<MutableList<TopRated>> = mutableStateOf(mutableListOf())
    val topRatedPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
    private val newTopRated = topRatedPageStateFlow.flatMapLatest {
        _topRatedLoadingState.value = NetworkState.LOADING
        topRatedRepository.loadTopRated(
            page = it,
            success = { _topRatedLoadingState.value = NetworkState.SUCCESS },
            error = { _topRatedLoadingState.value = NetworkState.ERROR }
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newTvFlow.collectLatest {
                tvs.value.addAll(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            newPopularFlow.collectLatest {
                populars.value.addAll(it)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            newTopRated.collectLatest {
                topRateds.value.addAll(it)
            }
        }
    }

    fun selectTab(tab: MainScreenHomeTab) {
        _selectedTab.value = tab
    }

    fun fetchNextMoviePage() {
        if (tvLoadingState.value != NetworkState.LOADING) {
            tvPageStateFlow.value++
        }
    }

    fun fetchNextPopularPage() {
        if (popularLoadingState.value != NetworkState.LOADING) {
            popularPageStateFlow.value++
        }
    }

    fun fetchNextTopRatedPage() {
        if (topRatedLoadingState.value != NetworkState.LOADING) {
            topRatedPageStateFlow.value++
        }
    }
}