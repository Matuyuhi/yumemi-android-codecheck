package jp.co.yumemi.android.code_check.data.repository

import jp.co.yumemi.android.code_check.data.http.ApiClient
import jp.co.yumemi.android.code_check.data.http.OwnerEntity
import jp.co.yumemi.android.code_check.data.http.RepositoryEntity
data class Owner(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val url: String,
) {
    companion object {
        fun fromGitHubOwner(gitHubOwner: OwnerEntity): Owner {
            return Owner(
                login = gitHubOwner.login,
                id = gitHubOwner.id,
                avatarUrl = gitHubOwner.avatarUrl,
                url = gitHubOwner.url,
            )
        }
    }
}
data class Repository(
    val id: Long,
    val name: String,
    val description: String,
    val owner: Owner,
    val url: String,
    val createdAt: String,
    val updatedAt: String,
    val startCount: Int,
    val watchersCount: Int,
    val language: String,
    val forksCount: Int,
    val openIssuesCount: Int,
    val licenceName: String,
    val topics: List<String>,
) {
    companion object {
        fun fromGitHubRepository(gitHubRepository: RepositoryEntity): Repository {
            return Repository(
                id = gitHubRepository.id,
                name = gitHubRepository.fullName,
                description = gitHubRepository.description ?: "",
                owner = Owner.fromGitHubOwner(gitHubRepository.owner),
                url = gitHubRepository.htmlUrl,
                createdAt = gitHubRepository.createdAt,
                updatedAt = gitHubRepository.updatedAt,
                startCount = gitHubRepository.stargazersCount,
                watchersCount = gitHubRepository.watchersCount,
                language = gitHubRepository.language ?: "",
                forksCount = gitHubRepository.forksCount,
                openIssuesCount = gitHubRepository.openIssuesCount,
                licenceName = gitHubRepository.license?.name ?: "",
                topics = gitHubRepository.topics,
            )
        }
    }
}


interface GitRepository {

    suspend fun getGitRepositoryList(searchText: String): List<Repository>
}
class GitRepositoryImpl(
    private val apiClient: ApiClient
) : GitRepository {

    override suspend fun getGitRepositoryList(searchText: String): List<Repository> {
        val response = apiClient.searchRepositories(searchText)
        return response.items.map {
            Repository.fromGitHubRepository(it)
        }
    }
}