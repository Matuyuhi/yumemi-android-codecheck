package jp.co.yumemi.android.code_check.data

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header


fun HttpRequestBuilder.defaultHeader() {
    header("Accept", "application/vnd.github.v3+json")
}