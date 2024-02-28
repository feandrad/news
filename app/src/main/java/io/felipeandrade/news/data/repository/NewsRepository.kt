package io.felipeandrade.news.data.repository

import io.felipeandrade.news.data.response.NewsResponse

interface NewsRepository {
    suspend fun getTopHeadlines(country: String): Result<NewsResponse>
}
