package com.rappi.android.ui.app.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.data.model.MovieItem
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.utils.BASE_IMAGE_URL

@Composable
fun MovieDetails(movieItem: MovieItem) {
    Column() {
        MovieDetailsBanner(movieItem = movieItem)
        MovieDetailsText(movieItem = movieItem)
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
fun MovieDetailsText(movieItem: MovieItem){
    Column(modifier = Modifier.padding(10.dp)) {
        movieItem.title?.let {
            Text(
                text = it,
                fontFamily = dmSansFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp)
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