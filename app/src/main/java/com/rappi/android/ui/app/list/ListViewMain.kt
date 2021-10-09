package com.rappi.android.ui.app.list

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rappi.android.data.model.MovieItem
import com.rappi.android.ui.viewmodel.MainViewModel

@ExperimentalFoundationApi
@Composable
fun PopularList(
    context: Context,
    mainViewModel: MainViewModel
){
    MovieList(context = context, movieList = mainViewModel.popularMovies)
}

@ExperimentalFoundationApi
@Composable
fun TopRatedList(
    context: Context,
    mainViewModel: MainViewModel
){
    MovieList(context = context, movieList = mainViewModel.topRatedMovies)
}

@ExperimentalFoundationApi
@Composable
fun MovieList(
    context: Context,
    movieList: List<MovieItem>
){
    val listState = rememberLazyListState()

    LazyVerticalGrid(state = listState, cells = GridCells.Fixed(2)) {
        /*stickyHeader {
            MainHeader()
        }*/
        itemsIndexed(movieList) { index, item ->
            ListViewItem(context = context, movieItem = item)
        }
    }
}