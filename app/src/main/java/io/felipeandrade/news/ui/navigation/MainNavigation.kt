package io.felipeandrade.news.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.felipeandrade.news.ui.screen.DetailsScreen
import io.felipeandrade.news.ui.screen.HomeScreen
import io.felipeandrade.news.util.decode

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(route = Routes.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = Routes.Details.route,
            arguments = Routes.Details.args
        ) {
            val title = it.arguments?.getString(Routes.Details.TITLE_ARG).decode()
            val imageUrl = it.arguments?.getString(Routes.Details.IMAGE_URL_ARG).decode()
            val description = it.arguments?.getString(Routes.Details.DESCRIPTION_ARG).decode()
            val sources = it.arguments?.getString(Routes.Details.SOURCES_ARG).decode()

            DetailsScreen(navController, title, imageUrl, description, sources)
        }
    }
}
