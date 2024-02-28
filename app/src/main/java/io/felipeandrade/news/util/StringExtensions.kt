package io.felipeandrade.news.util

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String?.encode(): String? =
    URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

fun String?.decode(): String = this?.let {
    URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
} ?: ""