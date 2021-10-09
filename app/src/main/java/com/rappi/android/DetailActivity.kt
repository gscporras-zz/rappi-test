package com.rappi.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import com.rappi.android.data.model.MovieItem
import com.rappi.android.ui.app.details.MovieDetails
import com.rappi.android.ui.viewmodel.MainViewModel

class DetailActivity: AppCompatActivity() {

    companion object {
        const val ID = "id"
    }

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainViewModel.fetchMovieDetail(intent?.extras?.getInt(ID))
            MovieDetails(mainViewModel.movie)
        }
    }
}