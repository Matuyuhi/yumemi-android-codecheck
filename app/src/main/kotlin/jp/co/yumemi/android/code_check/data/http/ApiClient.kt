package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class ApiClient {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json = Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }
    }

    suspend fun searchRepositories(query: String): GitHubSearchEntity {
        return withContext(Dispatchers.IO) {
            val response = client.get("$BASE_URL/search/repositories?q=$query") {
                defaultHeader()
            }
            response.body<GitHubSearchEntity>()
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}