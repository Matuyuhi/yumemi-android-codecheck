/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 *
 * after: edit by matuyuhi
 */
package jp.co.yumemi.android.code_check.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.data.http.SortType
import jp.co.yumemi.android.code_check.data.repository.GetSearchRepositories
import jp.co.yumemi.android.code_check.data.repository.GitRepository
import jp.co.yumemi.android.code_check.data.repository.Repository
import jp.co.yumemi.android.code_check.data.ui.UiEventException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * SearchScreen で使う
 */
class SearchViewModel(
    private val gitRepository: GitRepository
) : ViewModel() {
    private val _uiModel = MutableStateFlow(SearchUiModel())
    val uiModel = _uiModel.asStateFlow()

    private val _error = MutableStateFlow(UiEventException())
    val error = _error.asStateFlow()

    val eventListener = object : SearchScreenUiEvent {
        override val onInputTextChanged: (String) -> Unit = { inputText ->
            _uiModel.onInputTextChanged(inputText)
        }

        override val onSearchComplete: () -> Unit = {
            searchResults { result ->
                _uiModel.searchComplete(result.items)
            }
        }
        override val onErrorRetry: () -> Unit = {
            searchResults { result ->
                _uiModel.searchComplete(result.items)
            }
        }
        override val onErrorClose: () -> Unit = {
            _error.value = UiEventException()
        }
        override val onFetchMore: () -> Unit = onFetchMore@{
            if (uiModel.value.searchResults.size + SearchUiModel.PER_PAGE > SearchUiModel.MAX_SEARCH_RESULT) {
                return@onFetchMore
            }
            searchResults(offset = uiModel.value.searchResults.size) {
                _uiModel.addSearchResults(it.items)
            }
        }
        override val onSetSort: (SortType) -> Unit = { sortType ->
            _uiModel.setSort(sortType)
            // 再度検索をする
            if (uiModel.value.inputText.isNotEmpty()) {
                searchResults { result ->
                    _uiModel.searchComplete(result.items)
                }
            }
        }
    }

    // 検索結果
    fun searchResults(
        count: Int = SearchUiModel.PER_PAGE,
        offset: Int = 0,
        onCompleted: suspend (GetSearchRepositories) -> Unit
    ) {
        viewModelScope.launch {
            val inputText = uiModel.value.inputText
            if (inputText.isEmpty()) {
                return@launch
            }
            _uiModel.setLoading()
            runCatching {
                gitRepository.getGitRepositoryList(
                    inputText,
                    count = count,
                    offset = offset,
                    sort = uiModel.value.sortType
                )
            }.fold(
                onSuccess = { result ->
                    onCompleted(result)
                },
                onFailure = {
                    _error.value = UiEventException(it)
                    Log.e(TAG, "searchResults: $it")
                }
            ).also {
                _uiModel.stopLoading()
            }
        }
    }

    private suspend fun MutableStateFlow<SearchUiModel>.setLoading() {
        emit(value.copy(isLoading = true))
    }

    private suspend fun MutableStateFlow<SearchUiModel>.stopLoading() {
        emit(value.copy(isLoading = false))
    }

    private suspend fun MutableStateFlow<SearchUiModel>.searchComplete(
        items: List<Repository>
    ) {
        emit(
            value.copy(
                searchResults = items,
                isLoading = false,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }

    private suspend fun MutableStateFlow<SearchUiModel>.addSearchResults(
        items: List<Repository>
    ) {
        emit(
            value.copy(
                searchResults = value.searchResults + items,
                isLoading = false,
                lastUpdated = System.currentTimeMillis()
            )
        )
    }

    private fun MutableStateFlow<SearchUiModel>.onInputTextChanged(inputText: String) {
        value = value.copy(inputText = inputText)
    }

    private fun MutableStateFlow<SearchUiModel>.setSort(sortType: SortType) {
        value = value.copy(sortType = sortType)
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}

interface SearchScreenUiEvent {
    val onInputTextChanged: (inputText: String) -> Unit
    val onSearchComplete: () -> Unit
    val onErrorRetry: () -> Unit
    val onErrorClose: () -> Unit
    val onFetchMore: () -> Unit
    val onSetSort: (SortType) -> Unit
}
