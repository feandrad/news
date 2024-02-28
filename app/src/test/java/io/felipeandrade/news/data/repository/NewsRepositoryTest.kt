package io.felipeandrade.news.data.repository

import io.felipeandrade.news.data.api.NewsApiService
import io.felipeandrade.news.data.response.NewsResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class NewsRepositoryTest {

    private lateinit var newsApiService: NewsApiService
    private lateinit var newsRepository: NewsRepository

    @Before
    fun setUp() {
        newsApiService = mockk()
        newsRepository = NewsRepositoryImpl(newsApiService)
    }

    @Test
    fun `getTopHeadlines returns success when API call is successful`() = runBlockingTest {
        // Given
        val fakeSources = "bbc-news"
        val fakeNewsResponse = NewsResponse("ok", null, listOf())
        coEvery { newsApiService.getTopHeadlines(sources = fakeSources) } returns Response.success(fakeNewsResponse)

        // When
        val result = newsRepository.getTopHeadlines(fakeSources)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(fakeNewsResponse, result.getOrNull())
    }

    @Test
    fun `getTopHeadlines returns failure when API call is unsuccessful`() = runBlockingTest {
        // Given
        val fakeSources = "bbc-news"
        coEvery { newsApiService.getTopHeadlines(sources = fakeSources) } returns Response.error(400, mockk(relaxed = true))

        // When
        val result = newsRepository.getTopHeadlines(fakeSources)

        // Then
        assertTrue(result.isFailure)
        result.exceptionOrNull()?.let {
            assertEquals("Failed to load news", it.message)
        } ?: fail("Result should contain exception")
    }

    @Test
    fun `getTopHeadlines returns failure when API call throws an exception`() = runBlockingTest {
        // Given
        val fakeSources = "bbc-news"
        val exception = Exception("Network failure")
        coEvery { newsApiService.getTopHeadlines(sources = fakeSources) } throws exception

        // When
        val result = newsRepository.getTopHeadlines(fakeSources)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
