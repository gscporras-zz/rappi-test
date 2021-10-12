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

    object TvDetails : NavScreen("TvDetails") {

        const val routeWithArgument: String = "TvDetails/{tvId}"

        const val argument0: String = "tvId"
    }

    object PersonDetails : NavScreen("PersonDetails") {

        const val routeWithArgument: String = "PersonDetails/{personId}"

        const val argument0: String = "personId"
    }
}