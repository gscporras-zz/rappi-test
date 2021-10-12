package com.rappi.android.ui.navigation

import androidx.compose.runtime.Immutable
import com.rappi.android.ui.feature.main.MainScreenHomeTab

@Immutable
sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")

    object MovieDetails : NavScreen("MovieDetails") {

        const val routeWithArgument: String = "MovieDetails/{movieId}"

        const val argument0: String = "movieId"
    }

    object PopularDetails : NavScreen("PopularDetails") {

        const val routeWithArgument: String = "PopularDetails/{movieId}"

        const val argument0: String = "movieId"
    }

    object TopRatedDetails : NavScreen("TopRatedDetails") {

        const val routeWithArgument: String = "TopRatedDetails/{movieId}"

        const val argument0: String = "movieId"
    }
}