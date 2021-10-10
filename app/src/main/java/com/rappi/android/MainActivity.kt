package com.rappi.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rappi.android.ui.app.home.HomeList
import com.rappi.android.ui.app.list.MovieList
import com.rappi.android.ui.theme.RappiTestTheme
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.ui.viewmodel.MainViewModel
import com.rappi.android.utils.NavigationItem
import com.rappi.android.utils.startActivity

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val currentScreen = NavigationItem.fromRoute(navBackStackEntry?.destination?.route)

            RappiTestTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController, currentRoute = currentRoute)
                    }
                ) { innerPadding ->
                    Box {
                        Navigation(navController, innerPadding)
                        TopBar(currentScreen = currentScreen)
                    }
                }
            }
        }
    }

    @Composable
    fun TopBar(currentScreen: String?) {
        TopAppBar(
            title = { Text(text = currentScreen ?: "", fontFamily = dmSansFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            backgroundColor = Color.Transparent,
             modifier = Modifier.background(Brush.verticalGradient(
                listOf(Color.Black, colorResource(id = R.color.black_50), Color.Transparent)
            )),
            contentColor = colorResource(id = R.color.white),
            actions = {
                IconButton(onClick = { startActivity<SearchActivity>() }) {
                    Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "")
                }
            },
            elevation = 0.dp
        )
    }

    @Composable
    fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Popular,
            NavigationItem.TopRated
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.black),
            contentColor = colorResource(id = R.color.white)
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                    label = { Text(text = item.title) },
                    selectedContentColor = colorResource(id = R.color.white),
                    unselectedContentColor = colorResource(id = R.color.white_50),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
        NavHost(navController, startDestination = NavigationItem.Home.route, modifier = Modifier
            .padding(innerPadding)
            .background(Color.Black)) {
            composable(NavigationItem.Home.route) {
                mainViewModel.fetchMovieLatest()
                Surface(
                    color = Color.Black,
                    modifier = Modifier.fillMaxSize()
                ) {
                    HomeList(mainViewModel.movieLatest)
                }
            }
            composable(NavigationItem.Popular.route) {
                mainViewModel.fetchPopularMovies()
                Surface {
                    MovieList(mainViewModel.popularMovies)
                }
            }
            composable(NavigationItem.TopRated.route) {
                mainViewModel.fetchTopRatedMovies()
                Surface {
                    MovieList(mainViewModel.topRatedMovies)
                }
            }
        }
    }
}