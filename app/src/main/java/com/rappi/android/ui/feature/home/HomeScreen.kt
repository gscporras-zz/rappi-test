package com.rappi.android.ui.feature.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.palette.graphics.Palette
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.R
import com.rappi.android.models.entities.Movie
import com.rappi.android.models.network.NetworkState
import com.rappi.android.models.network.onLoading
import com.rappi.android.network.Api
import com.rappi.android.network.compose.NetworkImage
import com.rappi.android.ui.feature.main.MainScreenHomeTab
import com.rappi.android.ui.feature.main.MainViewModel
import com.rappi.android.ui.theme.Typography
import com.rappi.android.utils.paging
import com.rappi.android.utils.paging2
import com.skydoves.landscapist.palette.BitmapPalette
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    selectPoster: (MainScreenHomeTab, Int) -> Unit,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    val networkState: NetworkState by viewModel.movieLoadingState
    val movies by viewModel.movies

    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LazyColumn(
        state = lazyListState
    ) {

        paging2(
            items = movies,
            currentIndexFlow = viewModel.moviePageStateFlow,
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
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    /*Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            val (image, box, title) = createRefs()

            var palette by remember { mutableStateOf<Palette?>(null) }

            NetworkImage(
                networkUrl = Api.getPosterPath(movies.first().poster_path),
                bitmapPalette = BitmapPalette {
                    palette = it
                }
            )

            *//*Crossfade(
                targetState = palette,
                modifier = Modifier
                    .size(width.value.dp, height.value.dp)
                    *//**//*.constrainAs(box) {
                        top.linkTo(image.bottom)
                        bottom.linkTo(parent.bottom)
                    }*//**//*
            ) {

                Box(
                    modifier = Modifier
                        .background(Color(it?.darkVibrantSwatch?.rgb ?: 0))
                        .alpha(0.7f)
                        .fillMaxSize()
                )
            }*//*
            *//*Image(
                painter = rememberCoilPainter(request = Api.getPosterPath(movies.first().poster_path), previewPlaceholder = R.mipmap.ic_launcher, fadeIn = true),
                modifier = Modifier
                    .aspectRatio(0.6f)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    },
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )*//*
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
    }*/
}

@Composable
fun MoviePoster(
    movie: Movie,
    selectPoster: (MainScreenHomeTab, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    selectPoster(MainScreenHomeTab.HOME, movie.id ?: 0)
                }
            ),
        color = MaterialTheme.colors.onBackground
    ) {

        Box {
            //val (box, crossfade) = createRefs()

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
                    .background(Brush.verticalGradient(
                        listOf(Color.Black, Color.Transparent, Color.Black)))
            ){}
        }
    }
}