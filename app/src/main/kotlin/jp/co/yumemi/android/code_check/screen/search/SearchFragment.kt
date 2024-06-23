/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 *
 * after: edit by matuyuhi
 */
package jp.co.yumemi.android.code_check.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.repository.Repository
import jp.co.yumemi.android.code_check.data.ui.AppTheme
import jp.co.yumemi.android.code_check.screen.ResultScreenArgsModel.Companion.toResultArgs

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)

        composeView?.apply {
            setContent {
                AppTheme {
                    SearchScreen { item, updated ->
                        gotoRepositoryFragment(item, updated)
                    }
                }
            }
        }
    }

    private fun gotoRepositoryFragment(item: Repository, updated: Long) {
        val action = SearchFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item.toResultArgs(updated))
        findNavController().navigate(action)
    }
}