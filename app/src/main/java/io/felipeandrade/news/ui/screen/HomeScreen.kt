package io.felipeandrade.news.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.felipeandrade.news.R
import io.felipeandrade.news.domain.model.NewsArticle
import io.felipeandrade.news.ui.NewsViewModel
import io.felipeandrade.news.ui.navigation.Routes
import io.felipeandrade.news.ui.widget.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: NewsViewModel = hiltViewModel()
    val newsArticles by viewModel.newsArticles.observeAsState()
    val newsProvider by viewModel.newsProvider.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(newsProvider ?: stringResource(R.string.all_sources)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (error != null) {
                Text(
                    text = error ?: stringResource(R.string.unknown_error),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                // Display the list of news articles
                newsArticles?.let { articles ->
                    NewsList(
                        newsList = articles,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        onClick = { article ->
                            navController.navigateToDetails(article, newsProvider)
                        }
                    )
                } ?: Text(stringResource(R.string.no_news_available))
            }
        }
    }
}

private fun NavHostController.navigateToDetails(article: NewsArticle, sources: String?) {
    navigate(Routes.Details.createRoute(article.title, article.imageUrl?: "", article.description, sources))
}

@Composable
fun NewsList(newsList: List<NewsArticle>, onClick: (NewsArticle) -> Unit, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(newsList.sortedByDescending { it.publishedAt }) { news ->
            NewsItem(news, onClick)
        }
    }
}

@Composable
fun NewsItem(news: NewsArticle, onClick: (NewsArticle) -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable { onClick(news) }) {
        Row(modifier = Modifier.padding(8.dp)) {
            news.imageUrl?.let { url ->
                GlideImage(
                    imageUrl = url,
                    contentDescription = stringResource(R.string.news_image),
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

