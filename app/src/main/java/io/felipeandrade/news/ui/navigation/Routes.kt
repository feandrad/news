package io.felipeandrade.news.ui.navigation

import androidx.navigation.NamedNavArgument
import io.felipeandrade.news.util.encode

sealed class Routes(
    val route: String,
    val args: List<NamedNavArgument> = listOf()
) {
    object Home : Routes(route = "home")
    object Details : Routes(route = "details/{title}/{description}/{imageUrl}/{sources}") {
        const val TITLE_ARG = "title"
        const val IMAGE_URL_ARG = "imageUrl"
        const val DESCRIPTION_ARG = "description"
        const val SOURCES_ARG = "sources"

        fun createRoute(title: String, imageUrl: String, description: String, sources: String?): String =
            "details/${title.encode()}/${description.encode()}/${imageUrl.encode()}/${sources.encode()}"
    }
}
