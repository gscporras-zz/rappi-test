package com.rappi.android.ui.feature.main

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rappi.android.R
import com.rappi.android.ui.feature.detail.popular.PopularDetailScreen
import com.rappi.android.ui.feature.detail.search.SearchDetailScreen
import com.rappi.android.ui.feature.detail.toprated.TopRatedDetailScreen
import com.rappi.android.ui.feature.detail.tv.TvDetailScreen
import com.rappi.android.ui.feature.search.SearchScreen
import com.rappi.android.ui.navigation.NavScreen
import com.rappi.android.ui.theme.dmSansFamily

@Composable
fun MainScreen(currentScreen: String?) {
    val navController = rememberNavController()
    val tabStateHolder = HomeTabStateHolder(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState(),
    )

    NavHost(navController = navController, startDestination = NavScreen.Tv.route) {
        composable(route = NavScreen.Tv.route) {
            HomeTabScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                tabStateHolder = tabStateHolder,
                selectItem = { tab, index ->
                    when(tab) {
                        MainScreenHomeTab.TV -> navController.navigate("${NavScreen.MovieDetails.route}/$index")
                        MainScreenHomeTab.POPULAR -> navController.navigate("${NavScreen.PopularDetails.route}/$index")
                        MainScreenHomeTab.TOP_RATED -> navController.navigate("${NavScreen.TopRatedDetails.route}/$index")
                    }
                },
                currentScreen = currentScreen
            )
        }
        composable(route = NavScreen.Search.route) {
            SearchScreen(
                viewModel = hiltViewModel(),
                selectPoster = { index ->
                    navController.navigate("${NavScreen.Search.route}/$index")
                }
            ) {
                navController.navigateUp()
            }
        }
        composable(
            route = NavScreen.Search.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.Search.argument0) { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt(NavScreen.Search.argument0) ?: return@composable

            SearchDetailScreen(
                movieId = movieId,
                viewModel = hiltViewModel()
            ) {
                navController.navigateUp()
            }
        }
        composable(
            route = NavScreen.MovieDetails.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.MovieDetails.argument0) { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt(NavScreen.MovieDetails.argument0) ?: return@composable
            TvDetailScreen(movieId = movieId, viewModel = hiltViewModel()) {
                navController.navigateUp()
            }
        }

        composable(
            route = NavScreen.PopularDetails.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.PopularDetails.argument0) { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt(NavScreen.PopularDetails.argument0) ?: return@composable
            PopularDetailScreen(movieId = movieId, viewModel = hiltViewModel()) {
                navController.navigateUp()
            }
        }
        composable(
            route = NavScreen.TopRatedDetails.routeWithArgument,
            arguments = listOf(navArgument(NavScreen.TopRatedDetails.argument0) { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt(NavScreen.TopRatedDetails.argument0) ?: return@composable
            TopRatedDetailScreen(movieId = movieId, viewModel = hiltViewModel()) {
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun MainAppBar(navController: NavController, currentScreen: String? = "") {
    TopAppBar(
        title = { Text(text = currentScreen ?: "", fontFamily = dmSansFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
        backgroundColor = Color.Transparent,
        modifier = Modifier
            .height(58.dp)
            .background(
                Brush.verticalGradient(
                    listOf(Color.Black, colorResource(id = R.color.black_50), Color.Transparent)
                )
            ),
        contentColor = colorResource(id = R.color.white),
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(NavScreen.Search.route)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = ""
                )
            }
        },
        elevation = 0.dp
    )
}

@Immutable
enum class MainScreenHomeTab(
    @StringRes val title: Int,
    val icon: Int
) {
    TV(R.string.menu_tv, R.drawable.ic_tv),
    POPULAR(R.string.menu_popular, R.drawable.ic_popular),
    TOP_RATED(R.string.menu_top_rated, R.drawable.ic_top_rated);
}