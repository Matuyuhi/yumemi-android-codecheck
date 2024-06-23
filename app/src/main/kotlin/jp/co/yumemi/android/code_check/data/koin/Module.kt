package jp.co.yumemi.android.code_check.data.koin

import jp.co.yumemi.android.code_check.data.http.ApiClient
import jp.co.yumemi.android.code_check.data.repository.GitRepository
import jp.co.yumemi.android.code_check.data.repository.GitRepositoryImpl
import jp.co.yumemi.android.code_check.screen.result.ResultViewModel
import jp.co.yumemi.android.code_check.screen.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinApplication
import org.koin.dsl.module


val module = module {
    single<ApiClient> { ApiClient() }
    single<GitRepository> { GitRepositoryImpl(get()) }
    viewModel<SearchViewModel> { SearchViewModel(get()) }
    viewModel<ResultViewModel> { ResultViewModel() }
}

fun KoinApplication.loadModules() {
    modules(module)
}