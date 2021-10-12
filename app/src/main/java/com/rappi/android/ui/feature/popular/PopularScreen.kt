package com.rappi.android.ui.feature.popular
/*

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.rappi.android.R
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@ExperimentalFoundationApi
@InternalCoroutinesApi
@Composable
fun PopularScreen() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()

    // Listen for side effects from the VM
    LaunchedEffect(LAUNCH_LISTEN_FOR_EFFECTS) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is PopularContract.Effect.DataWasLoaded ->
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Food categories are loaded.",
                        duration = SnackbarDuration.Short
                    )
                is PopularContract.Effect.Navigation.MovieDetail -> onNavigationRequested(
                    effect
                )
            }
        }?.collect()
    }

    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            state = listState,
            cells = GridCells.Fixed(2)
        ) {
            itemsIndexed(state.movies.results) { index, item ->
                ListViewItem(movieItem = item, onEventSent = onEventSent)
            }

            item {
                Spacer(modifier = Modifier.padding(32.dp))
            }
        }
    }
}

@Composable
fun ListViewItem(
    movieItem: MovieItem,
    onEventSent: (event: PopularContract.Event) -> Unit,
) {
    val context = LocalContext.current
    ListViewItem(movieItem = movieItem, modifier = Modifier
        .background(Color.Black)
        .padding(8.dp)
        .clickable {
            //onEventSent(PopularContract.Event.PopularSelection(movieId = movieItem.id ?: 0))
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
}*/
