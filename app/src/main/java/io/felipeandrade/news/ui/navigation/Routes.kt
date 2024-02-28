package io.felipeandrade.news.ui.navigation

import androidx.navigation.NamedNavArgument

sealed class Routes(
    val route: String,
    val args: List<NamedNavArgument> = listOf()
) {
    object Home : Routes(route = "home") {
        object Dashboard : Routes("${Home.route}/dashboard")
        object Details : Routes("${Home.route}/details")

    }
}