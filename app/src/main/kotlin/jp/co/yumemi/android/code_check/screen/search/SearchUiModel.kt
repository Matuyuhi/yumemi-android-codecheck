package jp.co.yumemi.android.code_check.screen.search

import jp.co.yumemi.android.code_check.data.repository.Repository

data class SearchUiModel(
    val inputText: String = "",
    val searchResults: List<Repository> = emptyList(),
    val isLoading: Boolean = false,
    val lastUpdated: Long = 0,
)


