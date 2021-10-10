package com.rappi.android.utils

import com.rappi.android.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home: NavigationItem("home", R.drawable.ic_home, "Home")
    object Popular: NavigationItem("popular", R.drawable.ic_popular, "Popular")
    object TopRated: NavigationItem("topRated", R.drawable.ic_top_rated, "Top Rated")

    companion object {
        fun fromRoute(currentRoute: String?): String {
            return when(currentRoute) {
                Popular.route -> Popular.title
                TopRated.route -> TopRated.title
                else -> ""
            }
        }
    }
}