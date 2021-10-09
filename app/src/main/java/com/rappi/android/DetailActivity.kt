package com.rappi.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import com.rappi.android.ui.app.details.MovieDetails
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
            Column() {
                MovieDetails(mainViewModel.movie, mainViewModel.videos)
            }
        }
    }
}