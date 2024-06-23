package jp.co.yumemi.android.code_check.screen

import android.os.Parcelable
import jp.co.yumemi.android.code_check.data.repository.Repository
import jp.co.yumemi.android.code_check.data.ui.toDisplayFormattedString
import jp.co.yumemi.android.code_check.data.ui.toLocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultScreenArgsModel(
    val login: String,
    val avatarUrl: String,
    val url: String,
    val fullName: String,
    val createdAt: String,
    val updatedAt: String,
    val starCount: Int,
    val watchersCount: Int,
    val language: String,
    val forksCount: Int,
    val openIssuesCount: Int,
    val licenceName: String,
    val topics: List<String>,
    val searchUpdatedAt: String?
) : Parcelable {
    companion object {
        fun Repository.toResultArgs(searchUpdatedAt: Long?): ResultScreenArgsModel {
            return ResultScreenArgsModel(
                login = owner.login,
                avatarUrl = owner.avatarUrl,
                url = url,
                fullName = name,
                createdAt = createdAt.toDisplayFormattedString(),
                updatedAt = updatedAt.toDisplayFormattedString(),
                starCount = starCount,
                watchersCount = watchersCount,
                language = language,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount,
                licenceName = licenceName,
                topics = topics,
                searchUpdatedAt = searchUpdatedAt?.toLocalDateTime()?.toDisplayFormattedString()
            )
        }
    }
}