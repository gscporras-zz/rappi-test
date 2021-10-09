package com.rappi.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
                    topBar = {
                        if(currentRoute == NavigationItem.Popular.route ||  currentRoute == NavigationItem.TopRated.route) {
                            TopBar(currentScreen = currentScreen)
                        }
                             },
                    bottomBar = {
                        if(currentRoute == NavigationItem.Popular.route ||  currentRoute == NavigationItem.TopRated.route) {
                            BottomNavigationBar(navController = navController, currentRoute = currentRoute)
                        }
                    }) { innerPadding ->
                    Navigation(navController, innerPadding)
                }
            }
        }
    }

    @Composable
    fun TopBar(currentScreen: String?) {
        TopAppBar(
            title = { Text(text = currentScreen ?: "", fontFamily = dmSansFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black),
            actions = {
                IconButton(onClick = { startActivity<SearchActivity>() }) {
                    Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = "")
                }
            }
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
    fun Navigation(navController: NavHostController, innerPadding: PaddingValues) {
        NavHost(navController, startDestination = NavigationItem.Popular.route, modifier = Modifier.padding(innerPadding)) {
            composable(NavigationItem.Popular.route) {
                Surface(color = MaterialTheme.colors.background) {
                    PopularList(context = this@MainActivity, mainViewModel = mainViewModel)
                }
            }
            composable(NavigationItem.TopRated.route) {
                Surface(color = MaterialTheme.colors.background) {
                    TopRatedList(context = this@MainActivity, mainViewModel = mainViewModel)
                }
            }
        }
    }
}