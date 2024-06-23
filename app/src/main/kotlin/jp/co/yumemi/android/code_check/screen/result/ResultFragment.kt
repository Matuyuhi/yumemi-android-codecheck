package jp.co.yumemi.android.code_check.screen.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.ui.AppTheme

class ResultFragment : Fragment() {

    private val args by navArgs<ResultFragmentArgs>()

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

        val item = args.model
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)

        composeView?.apply {
            setContent {
                AppTheme {
                    ResultScreen(item)
                }
            }
        }

    }
}
