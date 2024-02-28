package io.felipeandrade.news.domain.usecase

import io.felipeandrade.news.data.repository.NewsRepository
import io.felipeandrade.news.data.response.Article
import io.felipeandrade.news.data.response.NewsResponse
import io.felipeandrade.news.data.response.Source
import io.felipeandrade.news.domain.model.NewsArticle
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class GetTopHeadlinesUseCaseTest {

    private val mockNewsRepository: NewsRepository = mockk()
    private val getTopHeadlinesUseCase = GetTopHeadlinesUseCase(mockNewsRepository)

    @Test
    fun `invoke returns mapped articles on success`() = runTest {
        // Given
        val fakeSource = "test_source"
        val fakeArticles = listOf(
            Article(
                Source("source1", "Source Name 1"),
                "Author 1",
                "Title 1",
                "Description 1",
                "Url 1",
                "ImageUrl 1",
                "PublishedAt 1",
                "Content 1"
            ),
            Article(
                Source("source2", "Source Name 2"),
                "Author 2",
                "Title 2",
                "Description 2",
                "Url 2",
                "ImageUrl 2",
                "PublishedAt 2",
                "Content 2"
            )
        )
        val fakeNewsResponse = NewsResponse("ok", 2, fakeArticles)

        coEvery { mockNewsRepository.getTopHeadlines(fakeSource) } returns Result.success(
            fakeNewsResponse
        )

        val expectedArticles = listOf(
            NewsArticle(
                title = "Title 1",
                imageUrl = "ImageUrl 1",
                description = "Description 1",
                publishedAt = "PublishedAt 1"
            ),
            NewsArticle(
                title = "Title 2",
                imageUrl = "ImageUrl 2",
                description = "Description 2",
                publishedAt = "PublishedAt 2"
            )
        )

        // When
        val result = getTopHeadlinesUseCase(fakeSource)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedArticles, result.getOrNull())
    }

    @Test
    fun `invoke returns error on failure`() = runTest {
        // Given
        val fakeSource = "test_source"
        val fakeError = RuntimeException("An error occurred")
        coEvery { mockNewsRepository.getTopHeadlines(fakeSource) } returns Result.failure(fakeError)

        // When
        val result = getTopHeadlinesUseCase(fakeSource)

        // Then
        assertTrue(result.isFailure)
        assertEquals("An error occurred", result.exceptionOrNull()?.message)
    }
}
