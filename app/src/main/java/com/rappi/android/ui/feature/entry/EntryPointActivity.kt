package com.rappi.android.ui.feature.entry

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rappi.android.R
import com.rappi.android.ui.feature.main.HomeTabStateHolder
import com.rappi.android.ui.feature.main.MainScreen
import com.rappi.android.ui.feature.main.MainViewModel
import com.rappi.android.ui.screen.search.SearchActivity
import com.rappi.android.ui.theme.RappiTestTheme
import com.rappi.android.ui.theme.dmSansFamily
import com.rappi.android.utils.fromRoute
import com.rappi.android.utils.startActivity
import com.skydoves.landscapist.coil.LocalCoilImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class EntryPointActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            CompositionLocalProvider(LocalCoilImageLoader provides viewModel.imageLoader) {
                RappiTestTheme {
                    MainScreen(currentRoute, currentRoute?.fromRoute())
                }
            }
        }
    }

    @InternalCoroutinesApi
    @ExperimentalFoundationApi
    @Composable
    fun RappiApp(navController: NavHostController, currentRoute: String?, currentScreen: String?) {
        val tabStateHolder = HomeTabStateHolder(
            rememberLazyListState(),
            rememberLazyListState(),
            rememberLazyListState(),
        )

        Scaffold(
            bottomBar = {
                //BottomNavigationBar(hiltViewModel(), navController = navController, currentRoute = currentRoute)
            }
        ) { innerPadding ->
            Box {
                //Nav(navController, innerPadding)
                TopBar(currentScreen = currentScreen)
            }
        }
    }

    @Composable
    fun TopBar(currentScreen: String?) {
        TopAppBar(
            title = { Text(text = currentScreen ?: "", fontFamily = dmSansFamily, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
            backgroundColor = Color.Transparent,
            modifier = Modifier.background(
                Brush.verticalGradient(
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

    /*@Composable
    fun BottomNavigationBar(viewModel: MainViewModel, navController: NavController, currentRoute: String?) {
        //val selectedTab by viewModel.selectedTab
        val tabs = MainScreenHomeTab.values()

        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Popular,
            NavigationItem.TopRated
        )
        BottomNavigation(
            backgroundColor = Color.Transparent,
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, colorResource(id = R.color.black_50), Color.Black)
                    )
                )
                .fillMaxWidth(),
            contentColor = Color.White,
            elevation = 0.dp
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
    }*/

    /*@InternalCoroutinesApi
    @Composable
    fun HomeDestination(navController: NavHostController) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state = viewModel.viewState.value
        HomeScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = { event -> viewModel.setEvent(event) },
            onNavigationRequested = { navigationEffect ->
                if (navigationEffect is HomeContract.Effect.Navigation.ToCategoryDetails) {
                    //navController.navigate("${NavigationItem.Route.FOOD_CATEGORIES_LIST}/${navigationEffect.categoryName}")
                }
            })
    }

    @ExperimentalFoundationApi
    @InternalCoroutinesApi
    @Composable
    fun PopularDestination(navController: NavHostController) {
        val viewModel: PopularViewModel = hiltViewModel()
        val state = viewModel.viewState.value
        PopularScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = { event -> viewModel.setEvent(event) },
            onNavigationRequested = { navigationEffect ->
                if (navigationEffect is PopularContract.Effect.Navigation.MovieDetail) {
                    //navController.navigate("${NavigationItem.Popular.route}/${navigationEffect.movieId}")
                }
            })
    }

    @ExperimentalFoundationApi
    @InternalCoroutinesApi
    @Composable
    fun TopRatedDestination(navController: NavHostController) {
        val viewModel: TopRatedViewModel = hiltViewModel()
        val state = viewModel.viewState.value
        TopRatedScreen(
            state = state,
            effectFlow = viewModel.effect,
            onEventSent = { event -> viewModel.setEvent(event) },
            onNavigationRequested = { navigationEffect ->
                if (navigationEffect is TopRatedContract.Effect.Navigation.MovieDetail) {
                    //navController.navigate("${NavigationItem.Popular.route}/${navigationEffect.movieId}")
                }
            })
    }*/
}