package com.rappi.android.ui.app.list

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.DetailActivity
import com.rappi.android.data.model.MovieItem
import com.rappi.android.utils.BASE_IMAGE_URL
import com.rappi.android.utils.NavigationItem

@Composable
fun ListViewItem(
    context: Context,
    navController: NavController? = null,
    movieItem: MovieItem
) {

    ListViewItem(movieItem = movieItem, modifier = Modifier
        .padding(8.dp)
        .clickable {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.ID, movieItem.id)
            context.startActivity(intent)
        })
}

@Composable
fun ListViewItem(
    movieItem: MovieItem, modifier: Modifier
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            MovieImageBanner(imagePath = movieItem.backdrop_path ?: "")
            MovieMetadataItem(movieItem = movieItem)
        }
    }
}

@Composable
fun MovieImageBanner(imagePath: String) {

    Image(
        modifier = Modifier
            .width(180.dp)
            .height(100.dp), painter = rememberCoilPainter(
            request = BASE_IMAGE_URL + imagePath
        ),
        contentDescription = ""
    )

}

@Composable
fun MovieMetadataItem(movieItem: MovieItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp)
    ) {
        movieItem.title?.let {
            Text(
                text = it
            )
            Text(
                text = movieItem.vote_average ?: "",
                style = MaterialTheme.typography.body1
            )
        }

    }
}