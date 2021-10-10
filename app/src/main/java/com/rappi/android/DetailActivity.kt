package com.rappi.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rappi.android.ui.app.details.MovieDetails
import com.rappi.android.ui.theme.RappiTestTheme
import com.rappi.android.ui.viewmodel.MainViewModel

class DetailActivity: AppCompatActivity() {

    companion object {
        const val ID = "id"
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.fetchMovieDetail(intent?.extras?.getInt(ID))
        mainViewModel.fetchVideosDetail(intent?.extras?.getInt(ID))
        setContent {
            RappiTestTheme {
                Surface(color = Color.Black) {
                    MovieDetails(mainViewModel.movie, mainViewModel.videos)
                }
            }
        }
    }
}