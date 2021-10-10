package com.rappi.android.ui.app.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.R
import com.rappi.android.data.model.MovieItem
import com.rappi.android.ui.app.list.ListViewItem
import com.rappi.android.utils.BASE_IMAGE_URL

@Composable
fun HomeList(movieList: List<MovieItem>) {
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }

    val imageUrl = remember { mutableStateOf("") }
    if(!movieList.isNullOrEmpty()) {
        imageUrl.value = BASE_IMAGE_URL + movieList.first().poster_path
    }

    Box {
        Image(
            painter = rememberCoilPainter(request = imageUrl.value, previewPlaceholder = R.mipmap.ic_launcher, fadeIn = true),
            modifier = Modifier
                .aspectRatio(0.6f)
                .onGloballyPositioned {
                    width.value = it.size.width / density
                    height.value = it.size.height / density
                },
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Column(
            Modifier
                .size(width.value.dp, height.value.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
        ) {}
    }
}