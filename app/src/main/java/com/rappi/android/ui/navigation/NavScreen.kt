package com.rappi.android.ui.navigation

import androidx.compose.runtime.Immutable
import com.rappi.android.ui.feature.main.MainScreenHomeTab

@Immutable
sealed class NavScreen(val route: String) {

    object Tv : NavScreen("tv")

    object Search : NavScreen("search") {

        const val routeWithArgument: String = "search/{movieId}"

        const val argument0: String = "movieId"
    }

    object MovieDetails : NavScreen("details") {

        const val routeWithArgument: String = "details/{movieId}"

        const val argument0: String = "movieId"
    }

    object PopularDetails : NavScreen("popular") {

        const val routeWithArgument: String = "popular/{movieId}"

        const val argument0: String = "movieId"
    }

    object TopRatedDetails : NavScreen("topRated") {

        const val routeWithArgument: String = "topRated/{movieId}"

        const val argument0: String = "movieId"
    }
}