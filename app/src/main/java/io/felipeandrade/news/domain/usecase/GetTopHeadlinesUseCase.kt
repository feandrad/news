package io.felipeandrade.news.domain.usecase

import io.felipeandrade.news.data.repository.NewsRepository
import io.felipeandrade.news.domain.model.NewsArticle

class GetTopHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(sources: String): Result<List<NewsArticle>> =
        newsRepository.getTopHeadlines(sources).map { resp ->
            resp.articles.map {
                NewsArticle(
                    title = it.title ?: "",
                    imageUrl = it.urlToImage ?: "",
                    publishedAt = it.publishedAt ?: "",
                )
            }
        }
}
