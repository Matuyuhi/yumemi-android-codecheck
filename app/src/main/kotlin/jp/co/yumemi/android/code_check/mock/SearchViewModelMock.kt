package jp.co.yumemi.android.code_check.mock

import jp.co.yumemi.android.code_check.data.http.ApiClient
import jp.co.yumemi.android.code_check.data.repository.GitRepository
import jp.co.yumemi.android.code_check.data.repository.GitRepositoryImpl
import jp.co.yumemi.android.code_check.screen.search.SearchViewModel

val apiClientMock: ApiClient = ApiClient()
val gitRepositoryMock: GitRepository = GitRepositoryImpl(apiClientMock)
val searchViewModelMock = SearchViewModel(gitRepositoryMock)