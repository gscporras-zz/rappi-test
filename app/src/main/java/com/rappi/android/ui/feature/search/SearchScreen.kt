package com.rappi.android.ui.feature.search

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.palette.graphics.Palette
import com.rappi.android.R
import com.rappi.android.models.entities.Search
import com.rappi.android.models.network.NetworkState
import com.rappi.android.models.network.onLoading
import com.rappi.android.network.Api
import com.rappi.android.network.compose.NetworkImage
import com.rappi.android.ui.feature.main.HomeTabStateHolder
import com.rappi.android.ui.theme.Typography
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.utils.hideKeyboard
import com.rappi.android.utils.paging
import com.skydoves.landscapist.palette.BitmapPalette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.ext.scope
import java.util.logging.Handler

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    selectPoster: (Int) -> Unit,
    pressOnBack: () -> Unit
) {
    val state = rememberLazyListState()
    val search by viewModel.search.collectAsState()

    Scaffold(
        backgroundColor = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            SearchedMovieList(viewModel, state, selectPoster)
            TopBar(viewModel, pressOnBack)
        }
    }
}

@Composable
fun TopBar(viewModel: SearchViewModel, pressOnBack: () -> Unit) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier.background(
            Brush.verticalGradient(
                listOf(Color.Black, colorResource(id = R.color.black_50), Color.Transparent)
            ))
    ) {
        SearchView(viewModel = viewModel, pressOnBack = pressOnBack)
    }
}

@Composable
fun SearchView(viewModel: SearchViewModel, pressOnBack: () -> Unit) {
    val text = remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()

    TextField(
        value = text.value,
        onValueChange = { value ->
            text.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp, fontFamily = dmSansFamily, fontWeight = FontWeight.Normal),
        placeholder = { Text("Busque una película", color = colorResource(id = R.color.white_50), style = Typography.body2) },
        leadingIcon = {
            IconButton(onClick = {
                pressOnBack.invoke()
            }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp),
                    tint = Color.White
                )
            }
        },
        trailingIcon = {
            if (text.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        text.value = TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        tint = colorResource(id = R.color.white_50)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            scope.launch(Dispatchers.IO) {
                viewModel.searchMovie(text.value.text)
            }
        })
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
fun SearchedMovieList(
    viewModel: SearchViewModel,
    lazyListState: LazyListState,
    selectPoster: (Int) -> Unit
) {
    val networkState: NetworkState by viewModel.movieLoadingState
    val movies by viewModel.movies.collectAsState()

    LazyColumn(
        state = lazyListState
    ) {
        if(movies.isNullOrEmpty()) {
            item {
                EmptyContent()
            }
        } else {
            paging(
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
    movie: Search,
    selectPoster: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    selectPoster(movie.id ?: 0)
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
                networkUrl = Api.getPosterPath(movie.poster_path ?: movie.profile_path),
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

            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = movie.title ?: movie.name ?: "",
                    style = Typography.h1,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = movie.media_type ?: "",
                    style = Typography.body2,
                    color = colorResource(id = R.color.white_50),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LazyItemScope.EmptyContent() {
    Box(
        modifier = Modifier.fillParentMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "No results", color = Color.White, style = Typography.body1)
    }
}