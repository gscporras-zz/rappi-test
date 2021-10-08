package com.rappi.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rappi.android.ui.app.list.PopularList
import com.rappi.android.ui.app.list.TopRatedList
import com.rappi.android.ui.theme.RappiTestTheme
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.ui.viewmodel.MainViewModel
import com.rappi.android.utils.NavigationItem

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
                    topBar = { TopBar(currentScreen = currentScreen) },
                    bottomBar = { BottomNavigationBar(navController = navController, currentRoute = currentRoute) }) {
                    Navigation(navController)
                }
            }
        }
    }

    @Composable
    fun TopBar(currentScreen: String?) {
        TopAppBar(
            title = { Text(text = currentScreen ?: "", fontFamily = dmSansFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black)
        )
    }

    @Composable
    fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
        val items = listOf(
            NavigationItem.Popular,
            NavigationItem.TopRated
        )
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black)
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                    label = { Text(text = item.title) },
                    selectedContentColor = colorResource(id = R.color.black),
                    unselectedContentColor = colorResource(id = R.color.black_30),
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
    fun Navigation(navController: NavHostController) {
        NavHost(navController, startDestination = NavigationItem.Popular.route) {
            composable(NavigationItem.Popular.route) {
                Surface(color = MaterialTheme.colors.background) {
                    PopularList(navController = navController, mainViewModel = mainViewModel)
                }
            }
            composable(NavigationItem.TopRated.route) {
                /*val id = it.arguments?.getString("id")
                requireNotNull(id)
                MovieDetails(mainViewModel.clickedItem, id)*/
                Surface(color = MaterialTheme.colors.background) {
                    TopRatedList(navController = navController, mainViewModel = mainViewModel)
                }
            }
        }
    }
}