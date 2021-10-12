package com.rappi.android.ui.feature.main

import androidx.compose.foundation.lazy.LazyListState

data class HomeTabStateHolder(
    val homeLazyListState: LazyListState,
    val popularLazyListState: LazyListState,
    val topRatedLazyListState: LazyListState,
)