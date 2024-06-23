package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header


fun HttpRequestBuilder.defaultHeader() {
    header("Accept", "application/json")
}