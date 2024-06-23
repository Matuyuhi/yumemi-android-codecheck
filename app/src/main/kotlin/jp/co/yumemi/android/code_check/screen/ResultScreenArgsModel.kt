package jp.co.yumemi.android.code_check.screen

import android.os.Parcelable
import jp.co.yumemi.android.code_check.data.repository.Repository
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultScreenArgsModel(
    val login: String,
    val avatarUrl: String,
    val url: String,
    val fullName: String,
    val createdAt: String,
    val updatedAt: String,
    val startCount: Int,
    val watchersCount: Int,
    val language: String,
    val forksCount: Int,
    val openIssuesCount: Int,
    val licenceName: String,
    val topics: List<String>,
): Parcelable {
    companion object {
        fun Repository.toResultArgs(): ResultScreenArgsModel {
            return ResultScreenArgsModel(
                login = owner.login,
                avatarUrl = owner.avatarUrl,
                url = url,
                fullName = name,
                createdAt = createdAt,
                updatedAt = updatedAt,
                startCount = startCount,
                watchersCount = watchersCount,
                language = language,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount,
                licenceName = licenceName,
                topics = topics
            )
        }
    }
}