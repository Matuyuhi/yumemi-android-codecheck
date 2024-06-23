/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 *
 * after: edit by matuyuhi
 */
package jp.co.yumemi.android.code_check.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.data.repository.GitRepository
import jp.co.yumemi.android.code_check.data.repository.Repository
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

    private val _error = MutableStateFlow<Throwable?>(null)
    val error = _error.asStateFlow()

    val eventListener = object : SearchScreenUiEvent {
        override fun onInputTextChanged(inputText: String) {
            _uiModel.onInputTextChanged(inputText)
        }

        override fun onSearchComplete() {
            searchResults()
        }
    }

    // 検索結果
    fun searchResults() {
        viewModelScope.launch {
            val inputText = uiModel.value.inputText
            if (inputText.isEmpty()) {
                return@launch
            }
            _uiModel.setLoading()
            runCatching {
                gitRepository.getGitRepositoryList(inputText)
            }.fold(
                onSuccess = { result ->
                    _uiModel.onSearchComplete(result.items)
                },
                onFailure = {
                    _error.value = it
                    println("error: $it")
                }
            )
        }
    }

    private suspend fun MutableStateFlow<SearchUiModel>.setLoading() {
        emit(value.copy(isLoading = true))
    }

    private suspend fun MutableStateFlow<SearchUiModel>.onSearchComplete(
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

    private fun MutableStateFlow<SearchUiModel>.onInputTextChanged(inputText: String) {
        value = value.copy(inputText = inputText)
    }
}

interface SearchScreenUiEvent {
    fun onInputTextChanged(inputText: String)
    fun onSearchComplete()
}
