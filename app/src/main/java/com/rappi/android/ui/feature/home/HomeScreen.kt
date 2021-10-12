package com.rappi.android.ui.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.rappi.android.models.entities.Tv
import com.rappi.android.models.network.NetworkState
import com.rappi.android.models.network.onLoading
import com.rappi.android.network.Api
import com.rappi.android.network.compose.NetworkImage
import com.rappi.android.ui.feature.main.MainScreenHomeTab
import com.rappi.android.ui.feature.main.MainViewModel
import com.rappi.android.utils.paging
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    selectPoster: (MainScreenHomeTab, Int) -> Unit,
    lazyListState: LazyListState
) {
    val networkState: NetworkState by viewModel.tvLoadingState
    val movies by viewModel.tvs

    LazyColumn(
        state = lazyListState
    ) {

        paging(
            items = movies,
            currentIndexFlow = viewModel.tvPageStateFlow,
            fetch = { viewModel.fetchNextMoviePage() }
        ) {

            MoviePoster(
                movie = it,
                selectPoster = selectPoster
            )
        }
    }

    networkState.onLoading {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp),
            )
        }
    }
}

@Composable
fun MoviePoster(
    movie: Tv,
    selectPoster: (MainScreenHomeTab, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    selectPoster(MainScreenHomeTab.TV, movie.id ?: 0)
                }
            ),
        color = MaterialTheme.colors.onBackground
    ) {

        Box {

            val density = LocalDensity.current.density
            val width = remember { mutableStateOf(0f) }
            val height = remember { mutableStateOf(0f) }

            var palette by remember { mutableStateOf<Palette?>(null) }

            Crossfade(
                targetState = palette,
                modifier = Modifier
                    .size(width.value.dp, height.value.dp)
            ) {

                Box(
                    modifier = Modifier
                        .background(Color(it?.darkVibrantSwatch?.rgb ?: 0))
                        .alpha(0.7f)
                        .fillMaxSize()
                )
            }

            NetworkImage(
                networkUrl = Api.getPosterPath(movie.poster_path),
                modifier = Modifier
                    .aspectRatio(0.6f)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    },
                bitmapPalette = BitmapPalette {
                    palette = it
                },
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .size(width.value.dp, height.value.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Black, Color.Transparent, Color.Black)
                        )
                    )
            ){}
        }
    }
}