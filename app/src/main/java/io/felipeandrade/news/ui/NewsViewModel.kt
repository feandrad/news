package io.felipeandrade.news.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.felipeandrade.news.BuildConfig
import io.felipeandrade.news.domain.model.EUNewsSources
import io.felipeandrade.news.domain.model.NewsArticle
import io.felipeandrade.news.domain.model.NewsSource
import io.felipeandrade.news.domain.model.USNewsSources
import io.felipeandrade.news.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
) : ViewModel() {

    private val _newsArticles = MutableLiveData<List<NewsArticle>>()
    val newsArticles: LiveData<List<NewsArticle>> = _newsArticles

    private val _newsProvider = MutableLiveData<NewsSource>()
    val newsProvider: LiveData<NewsSource> = _newsProvider

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        val initial = when (BuildConfig.NEWS_SOURCE) {
            "EU" -> EUNewsSources.BBC_NEWS
            else -> USNewsSources.CNN
        }
        changeNewsSource(initial)
    }

    fun loadNews(sources: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = getTopHeadlinesUseCase(sources)
                result.onSuccess { articles ->
                    _newsArticles.value = articles
                    _error.value = null
                }.onFailure { throwable ->
                    _error.value = throwable.message
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun changeNewsSource(source: NewsSource) {
        _newsProvider.value = source
        loadNews(source.id)
    }
}
