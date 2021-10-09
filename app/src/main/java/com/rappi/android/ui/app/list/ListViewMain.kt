package com.rappi.android.ui.app.list

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
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

    LazyColumn(state = listState) {
        /*stickyHeader {
            MainHeader()
        }*/
        itemsIndexed(movieList) { index, item ->
            ListViewItem(context = context, movieItem = item)
        }
    }
}