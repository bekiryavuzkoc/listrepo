package com.android.nesinecasestudy.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.android.nesinecasestudy.ui.homescreen.HomeScreen
import com.android.nesinecasestudy.ui.listscreen.compose.listview.ComposeListScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: NavigationRoute,
    onFinishApp: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<NavigationRoute.Home> {
            HomeScreen(onFinishApp = {
                onFinishApp()
            }, navigateToXMLDesign = {
                navController.navigate(NavigationRoute.XMLDesignScreen)
            }, navigateToComposeDesign = {
                navController.navigate(NavigationRoute.ComposeDesignScreen)
            })
        }

        composable<NavigationRoute.ComposeDesignScreen> {
            ComposeListScreen(
                onNavigateToPostDetail = { id, title, detail ->
                    navController.navigate(
                        NavigationRoute.ComposePostDetailScreen(
                            id = id,
                            title = title,
                            detail = detail
                        )
                    )
                },
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<NavigationRoute.XMLDesignScreen> {

        }

        composable<NavigationRoute.ComposePostDetailScreen> { backStackEntry ->
            val id: Int = backStackEntry.toRoute<NavigationRoute.ComposePostDetailScreen>().id
            val title: String = backStackEntry.toRoute<NavigationRoute.ComposePostDetailScreen>().title
            val body: String = backStackEntry.toRoute<NavigationRoute.ComposePostDetailScreen>().detail

//            PostDetailScreen(
//                movieId = id,
//                onBackClick = {
//                    navController.navigateUp()
//                },
//                onDiscoverPopularClick = {
//                    navController.navigate(NavigationRoute.PopularMovies) {
//                        popUpTo<NavigationRoute.Home> {
//                            inclusive = false
//                        }
//                    }
//                },
//            )

        }
    }
}