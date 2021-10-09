package com.rappi.android.ui.app.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.data.model.MovieItem
import com.rappi.android.data.model.VideoItem
import com.rappi.android.data.model.VideoResponse
import com.rappi.android.ui.app.video.ListVideoItem
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.utils.BASE_IMAGE_URL

@Composable
fun MovieDetails(movieItem: MovieItem, videos: List<VideoItem>) {
    Column() {
        MovieDetailsBanner(movieItem = movieItem)
        MovieDetailsText(movieItem = movieItem)
        MovieDetailsTrailers(videos = videos)
    }
}

@Composable
fun MovieDetailsBanner(movieItem: MovieItem){
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            painter = rememberCoilPainter(request = BASE_IMAGE_URL+movieItem.backdrop_path),
            contentDescription = ""
        )
    }
}
@Composable
fun MovieDetailsText(movieItem: MovieItem) {
    Column(modifier = Modifier.padding(10.dp)) {
        movieItem.title?.let {
            Text(
                text = it,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp)
        }
        Text(
            text = movieItem.overview ?: "",
            modifier = Modifier.padding(top=10.dp),
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

@Composable
fun MovieDetailsTrailers(videos: List<VideoItem>) {
    Column() {
        val listState = rememberLazyListState()

        Text(text = "Videos", fontFamily = dmSansFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
        LazyRow(state = listState) {
            itemsIndexed(videos) { index, item ->
                ListVideoItem(item)
            }
        }
    }
}
