package com.rappi.android.ui.screen.details
/*

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.R
import com.rappi.android.models.response.Cast
import com.rappi.android.models.response.MovieItem
import com.rappi.android.models.response.VideoItem
import com.rappi.android.ui.screen.video.ListVideoItem
import com.rappi.android.ui.theme.Typography
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.utils.BASE_IMAGE_URL
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun MovieDetails(movieItem: MovieItem, videos: List<VideoItem>, casts: List<Cast>, onClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }

    val score = remember { mutableStateOf(0) }
    score.value = movieItem.vote_average?.toInt() ?: 0

    val listState = rememberLazyListState()

    val genres = remember { mutableStateOf("") }
    genres.value = movieItem.genres?.first()?.name ?: ""

    val imageOffset = (-scrollState.value * 0.18f)

    val imageUrl = remember { mutableStateOf("") }
    imageUrl.value = BASE_IMAGE_URL+movieItem.poster_path

    val starts = listOf(
        Icons.Default.Star,
        Icons.Default.Star,
        Icons.Default.Star,
        Icons.Default.Star,
        Icons.Default.Star
    )
    */
/*Box(
        modifier = Modifier
            .background(color = colorResource(id = R.color.black))
            .fillMaxSize()) {
        MovieDetailsBanner(
            movieItem = movieItem,
            width = width,
            height = height,
            density = density,
            imageOffset = imageOffset,
            imagePath = imageUrl.value,
            listState = listState,
            genres = genres,
            starts = starts,
            score = score
        )
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .padding(top = height.value.dp)) {
            MovieDetailsText(movieItem = movieItem)
            MovieDetailsTrailers(videos = videos)
        }
    }*//*


    val state = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbarModifier = Modifier.background(color = colorResource(id = R.color.black)),
        toolbar = {
            val textSize = (18 + (30 - 18) * state.toolbarState.progress).sp

            Box(
                modifier = Modifier
                    .wrapContentHeight(),
                contentAlignment = Alignment.BottomCenter,

            ) {
                TopAppBar(
                    title = {
                        Text(
                            text =movieItem.title ?: "",
                            color = Color.White,
                            fontFamily = dmSansFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        ) },
                    backgroundColor = Color.Black,
                    navigationIcon = {
                        IconButton(onClick = { onClick.invoke() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                        }
                    },
                    elevation = 0.dp
                )

                */
/*IconButton(onClick = { onClick.invoke() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                }*//*

            }

            Image(
                modifier = Modifier
                    .pin()
                    .parallax(0.2f)
                    .aspectRatio(0.6f)
                    .graphicsLayer {
                        // change alpha of Image as the toolbar expands
                        alpha = state.toolbarState.progress
                    }
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    },
                painter = rememberCoilPainter(request = imageUrl.value),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            */
/*Column(
                modifier = Modifier
                    //.align(Alignment.BottomCenter)
                    .parallax(0.2f)
                    *//*
*/
/*.graphicsLayer {
                        // change alpha of Image as the toolbar expands
                        alpha = state.toolbarState.progress
                    }*//*
*/
/*
                    .padding(8.dp)
                    .road(Alignment.CenterStart, Alignment.BottomStart)
            ) {
                Text(
                    text = movieItem.title ?: "",
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.white),
                    style = Typography.h1,
                    textAlign = TextAlign.Center,
                    fontSize = textSize
                )
                Text(
                    text = genres.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    color = colorResource(id = R.color.white_80),
                    style = Typography.body2,
                    textAlign = TextAlign.Center,
                    fontSize = textSize
                )
                MovieDetailsReview(movieItem = movieItem, listState = listState, starts = starts, score = score)
            }*//*


            Column(modifier = Modifier
                .size(width.value.dp, height.value.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            colorResource(id = R.color.black_50),
                            colorResource(id = R.color.black)
                        )
                    )
                )
            ) {}

            Text(
                text = movieItem.title ?: "",
                color = Color.White,
                style = Typography.h2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .graphicsLayer {
                        // change alpha of Image as the toolbar expands
                        alpha = state.toolbarState.progress
                    }
                    .road(
                        whenCollapsed = Alignment.TopStart,
                        whenExpanded = Alignment.BottomCenter
                    ),
                textAlign = TextAlign.Center,
                fontSize = textSize
            )
    }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
            MovieDetailsText(movieItem = movieItem)
            MovieDetailsTrailers(videos = videos)
            MovieDetailsCast(casts = casts)
        }
    }
}

@Composable
fun MovieDetailsBanner(
    movieItem: MovieItem,
    width: MutableState<Float>,
    height: MutableState<Float>,
    density: Float,
    imageOffset: Float,
    imagePath: String,
    listState: LazyListState,
    genres: MutableState<String>,
    starts: List<ImageVector>,
    score: MutableState<Int>
) {
    Box {
        Image(
                modifier = Modifier
                    .aspectRatio(0.6f)
                    .onGloballyPositioned {
                        width.value = it.size.width / density
                        height.value = it.size.height / density
                    },
                painter = rememberCoilPainter(request = imagePath),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .size(width.value.dp, height.value.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                colorResource(id = R.color.black)
                            )
                        )
                    )
            ) {}
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        ) {
            Text(
                text = movieItem.title ?: "",
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(id = R.color.white),
                style = Typography.h1,
                textAlign = TextAlign.Center)
            Text(
                text = genres.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                color = colorResource(id = R.color.white_80),
                style = Typography.body2,
                textAlign = TextAlign.Center)
            MovieDetailsReview(movieItem = movieItem, listState = listState, starts = starts, score = score)
        }
    }
}

@Composable
fun MovieDetailsReview(movieItem: MovieItem, listState: LazyListState, starts: List<ImageVector>, score: MutableState<Int>) {
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)) {
        Text(
            text = movieItem.vote_average.toString(),
            modifier = Modifier.padding(end = 16.dp),
            style = Typography.body2,
            color = colorResource(id = R.color.white)
        )
        LazyRow(
            state = listState
        ) {
            itemsIndexed(starts) { index, item ->
                Icon(
                    imageVector = Icons.Default.Star,
                    modifier = Modifier.height(12.dp),
                    contentDescription = "",
                    tint = if(index <= score.value/2) colorResource(id = R.color.white) else colorResource(id = R.color.white_20)
                )
            }
        }
    }
}

@Composable
fun MovieDetailsText(movieItem: MovieItem) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp)
    ) {
        Text(
            text = movieItem.overview ?: "",
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(16.dp),
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

@Composable
fun MovieDetailsTrailers(videos: List<VideoItem>) {
    Column(Modifier.padding(top = 16.dp)) {
        val listState = rememberLazyListState()

        val youtubeVideos = videos.filter { it.official == true && it.type == "Trailer" && it.site == "YouTube" }
        if(!youtubeVideos.isNullOrEmpty()) {
            Text(
                text = "Trailers",
                color = colorResource(id = R.color.white),
                style = Typography.h2,
                modifier = Modifier.padding(start = 16.dp)
            )
            LazyRow(state = listState, modifier = Modifier.padding(top = 8.dp)) {
                itemsIndexed(youtubeVideos) { index, item ->
                    when(index) {
                        0 -> ListVideoItem(item, Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp))
                        youtubeVideos.lastIndex -> ListVideoItem(item, Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp))
                        else -> ListVideoItem(item, Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsCast(casts: List<Cast>) {
    val listState = rememberLazyListState()
    Column(Modifier.padding(top = 16.dp)) {
        Text(
            text = "Cast",
            color = colorResource(id = R.color.white),
            style = Typography.h2,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyRow(state = listState, modifier = Modifier.padding(top = 8.dp)) {
            itemsIndexed(casts.take(10)) { index, item ->
                if(item.known_for_department == "Acting") {
                    Column(modifier = Modifier
                        .width(140.dp)
                        .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            painter = rememberCoilPainter(request = BASE_IMAGE_URL+item.profile_path, fadeIn = true),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center
                        )
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = item.original_name ?: "",
                            style = Typography.body1,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = item.character ?: "",
                            style = Typography.body1,
                            fontSize = 10.sp,
                            color = colorResource(id = R.color.white_50)
                        )
                    }
                }
            }
        }
    }
}
*/
