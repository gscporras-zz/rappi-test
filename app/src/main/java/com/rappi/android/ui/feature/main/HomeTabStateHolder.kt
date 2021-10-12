package com.rappi.android.ui.feature.main

import androidx.compose.foundation.lazy.LazyListState

data class HomeTabStateHolder(
    val homeLazyListState: LazyListState,
    val tvLazyListState: LazyListState,
    val peopleLazyListState: LazyListState,
)