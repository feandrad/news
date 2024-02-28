package io.felipeandrade.news.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.felipeandrade.news.data.api.NewsApiService
import io.felipeandrade.news.data.repository.NewsRepository
import io.felipeandrade.news.data.repository.NewsRepositoryImpl
import io.felipeandrade.news.domain.usecase.GetTopHeadlinesUseCase

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideNewsRepository(apiService: NewsApiService): NewsRepository =
        NewsRepositoryImpl(apiService)

    @Provides
    fun provideGetTopHeadlinesUseCase(newsRepository: NewsRepository): GetTopHeadlinesUseCase =
        GetTopHeadlinesUseCase(newsRepository)
}
