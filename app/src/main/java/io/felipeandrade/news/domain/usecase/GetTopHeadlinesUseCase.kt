package io.felipeandrade.news.domain.usecase

import io.felipeandrade.news.data.repository.NewsRepository
import io.felipeandrade.news.domain.model.NewsArticle
import java.time.Instant
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class GetTopHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(sources: String): Result<List<NewsArticle>> =
        newsRepository.getTopHeadlines(sources).map { resp ->
            resp.articles.map {
                NewsArticle(
                    title = it.title ?: "",
                    imageUrl = it.urlToImage,
                    description = it.description ?: "",
                    publishedAt = it.publishedAt ?: "",
                )
            }.sortedByDescending { article ->
                try {
                    OffsetDateTime.parse(article.publishedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant()
                } catch (e: Exception) {
                    Instant.MIN
                }
            }
        }
}
