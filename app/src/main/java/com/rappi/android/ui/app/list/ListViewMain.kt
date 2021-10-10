package com.rappi.android.ui.app.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rappi.android.data.model.MovieItem
import com.rappi.android.ui.app.home.HomeList
import com.rappi.android.ui.viewmodel.MainViewModel

@ExperimentalFoundationApi
@Composable
fun MovieList(movieList: List<MovieItem>){
    val listState = rememberLazyListState()

    LazyVerticalGrid(state = listState, cells = GridCells.Fixed(2)) {
        /*stickyHeader {
            MainHeader()
        }*/
        itemsIndexed(movieList) { index, item ->
            ListViewItem(movieItem = item)
        }
    }
}