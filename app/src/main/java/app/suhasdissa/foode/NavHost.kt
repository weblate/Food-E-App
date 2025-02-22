package app.suhasdissa.foode

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import app.suhasdissa.foode.ui.components.TwoPaneScreen
import app.suhasdissa.foode.ui.screens.AboutScreen
import app.suhasdissa.foode.ui.screens.AdditiveDetailScreen
import app.suhasdissa.foode.ui.screens.HomeScreen
import app.suhasdissa.foode.ui.screens.SearchScreen
import app.suhasdissa.foode.ui.screens.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController, modifier: Modifier = Modifier, isLargeScreen: Boolean
) {
    AnimatedNavHost(
        navController = navController, startDestination = Home.route, modifier = modifier
    ) {
        composable(route = Home.route) {
            var additiveID by remember { mutableStateOf(0) }
            if (isLargeScreen) {
                TwoPaneScreen(PaneOne = {
                    HomeScreen(onClickTextCard = {
                        additiveID = it
                    }, onClickSearch = {
                        navController.navigateTo(SearchView.route)
                    }, onClickSettings = {
                        navController.navigateTo(Settings.route)
                    })
                }, PaneTwo = {
                    AdditiveDetailScreen(additiveID)
                })
            } else {
                HomeScreen(onClickTextCard = { id ->
                    navController.navigateTo("${AdditiveDetail.route}/$id")
                }, onClickSearch = {
                    navController.navigateTo(SearchView.route)
                }, onClickSettings = {
                    navController.navigateTo(Settings.route)
                })
            }
        }
        composable(route = Settings.route) {
            if (isLargeScreen) {
                TwoPaneScreen(PaneOne = {
                    SettingsScreen(onAboutClick = { })
                }, PaneTwo = { AboutScreen() })
            } else {
                SettingsScreen(onAboutClick = {
                    navController.navigateTo(About.route)
                })
            }
        }
        composable(route = About.route) {
            AboutScreen()
        }
        composable(route = SearchView.route) {
            var additiveID by remember { mutableStateOf(0) }
            if (isLargeScreen) {
                TwoPaneScreen(PaneOne = {
                    SearchScreen(onClickTextCard = { additiveID = it })
                }, PaneTwo = { AdditiveDetailScreen(additiveID) })
            } else {
                SearchScreen(onClickTextCard = { id ->
                    navController.navigateTo("${AdditiveDetail.route}/$id")
                })
            }
        }
        composable(
            route = AdditiveDetail.routeWithArgs, arguments = AdditiveDetail.arguments
        ) {
            val id = it.arguments?.getInt("AdditiveID")
            if (id != null) {
                AdditiveDetailScreen(id)
            }
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}