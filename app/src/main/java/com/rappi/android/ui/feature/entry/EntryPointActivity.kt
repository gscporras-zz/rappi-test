package com.rappi.android.ui.feature.entry

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rappi.android.ui.feature.main.MainScreen
import com.rappi.android.ui.feature.main.MainViewModel
import com.rappi.android.ui.feature.search.SearchViewModel
import com.rappi.android.ui.theme.RappiTestTheme
import com.rappi.android.utils.fromRoute
import com.skydoves.landscapist.coil.LocalCoilImageLoader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryPointActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            CompositionLocalProvider(LocalCoilImageLoader provides viewModel.imageLoader, LocalCoilImageLoader provides searchViewModel.imageLoader) {
                RappiTestTheme {
                    MainScreen(currentRoute?.fromRoute())
                }
            }
        }
    }
}