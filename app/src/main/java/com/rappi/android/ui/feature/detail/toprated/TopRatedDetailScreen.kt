package com.rappi.android.ui.feature.detail.toprated

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.palette.graphics.Palette
import com.google.accompanist.coil.rememberCoilPainter
import com.rappi.android.R
import com.rappi.android.models.Cast
import com.rappi.android.models.Video
import com.rappi.android.models.entities.Popular
import com.rappi.android.models.entities.TopRated
import com.rappi.android.network.Api
import com.rappi.android.network.compose.NetworkImage
import com.rappi.android.ui.custom.RatingBar
import com.rappi.android.ui.feature.detail.popular.PopularViewModel
import com.rappi.android.ui.theme.Typography
import com.rappi.android.ui.theme.dmSansFamily
import com.skydoves.landscapist.palette.BitmapPalette
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun TopRatedDetailScreen(
    movieId: Int?,
    viewModel: TopRatedViewModel,
    pressOnBack: () -> Unit
) {
    val movie: TopRated? by viewModel.movieFlow.collectAsState(initial = null)

    val stateToolbar = rememberCollapsingToolbarScaffoldState()
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }
    val mov = remember { mutableStateOf(TopRated()) }
    movie?.let { mov.value = it }
    val imageUrl = remember { mutableStateOf("") }
    movie?.let { imageUrl.value = Api.getPosterPath(it.poster_path) }

    LaunchedEffect(key1 = movieId) {
        viewModel.fetchMovieDetailsById(movieId ?: 0)
    }

    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = stateToolbar,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbarModifier = Modifier.background(color = colorResource(id = R.color.black)),
            toolbar = {
                val textSize = (18 + (30 - 18) * stateToolbar.toolbarState.progress).sp

                Box(
                    modifier = Modifier
                        .wrapContentHeight(),
                    contentAlignment = Alignment.BottomCenter,

                    ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = mov.value.title ?: "",
                                color = Color.White,
                                fontFamily = dmSansFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            ) },
                        backgroundColor = Color.Black,
                        navigationIcon = {
                            IconButton(onClick = { pressOnBack.invoke() }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                            }
                        },
                        elevation = 0.dp
                    )
                }

                Image(
                    modifier = Modifier
                        .pin()
                        .parallax(0.2f)
                        .aspectRatio(0.6f)
                        .graphicsLayer {
                            // change alpha of Image as the toolbar expands
                            alpha = stateToolbar.toolbarState.progress
                        }
                        .onGloballyPositioned {
                            width.value = it.size.width / density
                            height.value = it.size.height / density
                        },
                    painter = rememberCoilPainter(request = imageUrl.value),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .graphicsLayer {
                            // change alpha of Image as the toolbar expands
                            alpha = stateToolbar.toolbarState.progress
                        }
                        .road(
                            whenCollapsed = Alignment.TopStart,
                            whenExpanded = Alignment.BottomCenter
                        )
                ) {
                    Text(
                        text = movie?.title ?: "",
                        color = Color.White,
                        style = Typography.h1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = textSize
                    )

                    Text(
                        text = "Release Date: ${movie?.release_date}",
                        color = Color.White,
                        style = Typography.body2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )

                    RatingBar(
                        rating = (movie?.vote_average ?: 0f) / 2f,
                        color = Color.White,
                        modifier = Modifier
                            .height(15.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
            ) {
                MovieDetailsText(viewModel = viewModel)
                MovieDetailsTrailers(viewModel = viewModel)
                MovieDetailsCast(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MovieDetailsText(viewModel: TopRatedViewModel) {
    val movie: TopRated? by viewModel.movieFlow.collectAsState(initial = null)

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 60.dp)
    ) {
        Text(
            text = "Summary",
            style = Typography.h2,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Text(
            text = movie?.overview ?: "",
            color = colorResource(id = R.color.white),
            modifier = Modifier.padding(8.dp),
            fontFamily = dmSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }
}

@Composable
fun MovieDetailsTrailers(viewModel: TopRatedViewModel) {
    val videos: List<Video>? by viewModel.videoListFlow.collectAsState(initial = null)

    videos?.let {
        Column(Modifier.padding(top = 16.dp)) {
            val listState = rememberLazyListState()

            Text(
                text = "Trailers",
                color = Color.White,
                style = Typography.h2,
                modifier = Modifier.padding(8.dp)
            )
            LazyRow(
                state = listState,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(it) { video ->
                    VideoThumbnail(video)
                }
            }
        }
    }
}

@Composable
fun VideoThumbnail(video: Video) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {

        ConstraintLayout(
            modifier = Modifier
                .size(320.dp, 180.dp)
                .clickable(
                    onClick = {
                        val playVideoIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(Api.getYoutubeVideoPath(video.key))
                            )
                        context.startActivity(playVideoIntent)
                    }
                )
        ) {
            val (thumbnail, icon, box, title) = createRefs()

            var palette by remember { mutableStateOf<Palette?>(null) }
            NetworkImage(
                networkUrl = Api.getYoutubeThumbnailPath(video.key),
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(thumbnail) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                bitmapPalette = BitmapPalette {
                    palette = it
                }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_youtube),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Crossfade(
                targetState = palette,
                modifier = Modifier
                    .height(25.dp)
                    .constrainAs(box) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {

                Column(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black, Color.Black)
                            )
                        )) {}

                Box(
                    modifier = Modifier
                        .background(Color(it?.darkVibrantSwatch?.rgb ?: 0))
                        .alpha(0.7f)
                        .height(0.dp)
                )
            }

            Text(
                text = video.name,
                style = Typography.body2,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.85f)
                    .padding(horizontal = 8.dp)
                    .constrainAs(title) {
                        top.linkTo(box.top)
                        bottom.linkTo(box.bottom)
                    }
            )
        }
    }
}

@Composable
fun MovieDetailsCast(viewModel: TopRatedViewModel) {
    val casts: List<Cast>? by viewModel.castListFlow.collectAsState(initial = null)

    casts?.let {
        val listState = rememberLazyListState()
        Column(Modifier.padding(top = 8.dp)) {
            Text(
                text = "Cast",
                color = colorResource(id = R.color.white),
                style = Typography.h2,
                modifier = Modifier.padding(8.dp)
            )
            LazyRow(
                state = listState,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(it) { cast ->
                    if(cast.known_for_department == "Acting") {
                        Actor(cast)
                    }
                }
            }
        }
    }
}

@Composable
fun Actor(cast: Cast) {
    Column(modifier = Modifier
        .width(120.dp)
        .padding(8.dp)
    ) {
        NetworkImage(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(CircleShape),
            networkUrl = Api.getPosterPath(cast.profile_path),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = cast.name,
            style = Typography.body1,
            fontSize = 12.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = cast.character,
            style = Typography.body1,
            fontSize = 10.sp,
            color = colorResource(id = R.color.white_50),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}