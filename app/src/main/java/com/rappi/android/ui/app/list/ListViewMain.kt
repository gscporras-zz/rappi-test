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
    navController: NavController,
    mainViewModel: MainViewModel
){
    MovieList(context = context, navController = navController, movieList = mainViewModel.popularMovies)
}

@ExperimentalFoundationApi
@Composable
fun TopRatedList(
    context: Context,
    navController: NavController,
    mainViewModel: MainViewModel
){
    MovieList(context = context, navController = navController,movieList = mainViewModel.topRatedMovies)
}

@ExperimentalFoundationApi
@Composable
fun MovieList(
    context: Context,
    navController: NavController? = null,
    movieList: List<MovieItem>
){
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        /*stickyHeader {
            MainHeader()
        }*/
        itemsIndexed(movieList) { index, item ->
            ListViewItem(context = context, navController = navController, movieItem = item)
        }
    }
}