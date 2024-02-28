package io.felipeandrade.news.domain.model

data class NewsArticle(
    val title: String,
    val publishedAt: String,
    val imageUrl: String? = null,
)
