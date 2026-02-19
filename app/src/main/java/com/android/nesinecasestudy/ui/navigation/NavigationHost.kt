package com.android.nesinecasestudy.ui.navigation

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.toRoute
import com.android.nesinecasestudy.R
import com.android.nesinecasestudy.ui.homescreen.HomeScreen
import com.android.nesinecasestudy.ui.listscreen.compose.listview.ComposeListScreen
import com.android.nesinecasestudy.ui.postdetailscreen.compose.PostDetailScreen

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
                onNavigateToPostDetail = { title, detail ->
                    navController.navigate(
                        NavigationRoute.ComposePostDetailScreen(
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
            AndroidView(
                factory = { context ->
                    FragmentContainerView(context).apply {
                        id = View.generateViewId()
                    }
                },
                update = { container ->
                    val fragmentManager =
                        (container.context as FragmentActivity)
                            .supportFragmentManager

                    if (fragmentManager.findFragmentById(container.id) == null) {

                        val navHostFragment = NavHostFragment.create(R.navigation.xml_nav_graph)

                        fragmentManager.beginTransaction()
                            .replace(container.id, navHostFragment)
                            .setPrimaryNavigationFragment(navHostFragment)
                            .commitNow()
                    }
                }
            )
        }
        composable<NavigationRoute.ComposePostDetailScreen> { backStackEntry ->
            val title: String = backStackEntry.toRoute<NavigationRoute.ComposePostDetailScreen>().title
            val body: String = backStackEntry.toRoute<NavigationRoute.ComposePostDetailScreen>().detail

            PostDetailScreen(
                title = title,
                body = body,
                onNavigateBack = {
                    navController.navigateUp()
                },
            )
        }
    }
}