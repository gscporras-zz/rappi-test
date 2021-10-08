package com.rappi.android.utils

import com.rappi.android.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Popular: NavigationItem("popular", R.drawable.ic_popular, "Popular")
    object TopRated: NavigationItem("topRated", R.drawable.ic_top_rated, "Top Rated")

    companion object {
        fun fromRoute(currentRoute: String?): String {
            return if(Popular.route == currentRoute) Popular.title else TopRated.title
        }
    }
}