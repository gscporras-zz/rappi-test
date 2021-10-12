package com.rappi.android.ui.screen.list
/*

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.rappi.android.models.response.MovieItem
import com.rappi.android.ui.state.ErrorItem
import com.rappi.android.ui.state.LoadingItem
import com.rappi.android.ui.state.LoadingView
import kotlinx.coroutines.flow.Flow

@ExperimentalFoundationApi
@Composable
fun MovieList(movies: Flow<PagingData<MovieItem>>) {
    val lazyMovieItems = movies.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        */
/*stickyHeader {
            MainHeader()
        }*//*

        items(lazyMovieItems) { item ->
            //item?.let { ListViewItem(movieItem = item) }
        }

        lazyMovieItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyMovieItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyMovieItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}*/
