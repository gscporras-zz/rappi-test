package com.rappi.android.ui.app.list

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.DetailActivity
import com.rappi.android.R
import com.rappi.android.data.model.MovieItem
import com.rappi.android.ui.theme.Typography
import com.rappi.android.utils.BASE_IMAGE_URL

@Composable
fun ListViewItem(movieItem: MovieItem) {
    val context = LocalContext.current
    ListViewItem(movieItem = movieItem, modifier = Modifier
        .background(Color.Black)
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
    Cover(
        title = movieItem.title ?: "",
        imagePath = movieItem.poster_path ?: "",
        modifier = modifier
    )
}

@Composable
fun Cover(
    title: String,
    imagePath: String,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }
    Box(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(3.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            Box {
                Image(
                    painter = rememberCoilPainter(
                        request = BASE_IMAGE_URL + imagePath
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .aspectRatio(0.6f)
                        .onGloballyPositioned {
                            width.value = it.size.width / density
                            height.value = it.size.height / density
                        },
                    contentScale = ContentScale.Crop,
                )
                Column(
                    Modifier
                        .size(width.value.dp, height.value.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    colorResource(id = R.color.black_30),
                                    colorResource(id = R.color.black)
                                )
                            )
                        )
                ) {}
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = title,
                        style = Typography.h2,
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}