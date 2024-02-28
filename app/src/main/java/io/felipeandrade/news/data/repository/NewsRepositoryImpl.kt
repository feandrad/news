package io.felipeandrade.news.data.repository

import io.felipeandrade.news.data.api.NewsApiService
import io.felipeandrade.news.data.response.NewsResponse

class NewsRepositoryImpl(private val newsApiService: NewsApiService) : NewsRepository {
    override suspend fun getTopHeadlines(sources: String): Result<NewsResponse> {
        return try {
            val response = newsApiService.getTopHeadlines(sources = sources)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(RuntimeException("Failed to load news"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
