package io.felipeandrade.news.domain.model

import androidx.annotation.StringRes
import io.felipeandrade.news.R

enum class NewsSources(val id: String, @StringRes val displayName: Int) {
    ABC_NEWS("abc-news", R.string.source_abc_news),
    BBC_NEWS("bbc-news", R.string.source_bbc_news),
    AL_JAZEERA("al-jazeera-english", R.string.source_al_jazeera)
}