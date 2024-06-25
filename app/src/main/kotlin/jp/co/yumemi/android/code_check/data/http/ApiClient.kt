package jp.co.yumemi.android.code_check.data.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

enum class SortType(val value: String) {
    Stars("stars"),
    Forks("forks"),
    Helped("help-wanted-issues"),
    Updated("updated")
}

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

    suspend fun searchRepositories(
        query: String,
        sort: SortType = SortType.Stars,
        count: Int,
        offset: Int
    ): GitHubSearchEntity {
        return withContext(Dispatchers.IO) {
            val response = client.get("$BASE_URL/search/repositories") {
                defaultHeader()
                parameter("q", query)
                parameter("sort", sort.value)
                parameter("order", "desc")
                parameter("per_page", count)
                parameter("page", offset / count + 1)
            }
            response.body<GitHubSearchEntity>()
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}