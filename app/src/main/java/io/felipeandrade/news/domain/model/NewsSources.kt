package io.felipeandrade.news.domain.model

import androidx.annotation.StringRes
import io.felipeandrade.news.R

interface NewsSource {
    val id: String
    val displayName: Int
}


enum class EUNewsSources(override val id: String, @StringRes override val displayName: Int) :
    NewsSource {
    ABC_NEWS("abc-news", R.string.source_abc_news),
    BBC_NEWS("bbc-news", R.string.source_bbc_news),
    AL_JAZEERA("al-jazeera-english", R.string.source_al_jazeera)
}

enum class USNewsSources(override val id: String, @StringRes override val displayName: Int) :
    NewsSource {
    CNN("cnn", R.string.source_cnn_news),
    FOX_NEWS("fox-news", R.string.source_fox_news),
    NBC_NEWS("nbc-news", R.string.source_nbc_news)
}