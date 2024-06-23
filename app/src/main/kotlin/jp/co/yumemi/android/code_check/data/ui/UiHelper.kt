package jp.co.yumemi.android.code_check.data.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Loading(
    modifier: Modifier = Modifier,
    isVisible: Boolean
) {
    if (!isVisible) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScrimMedium),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary
        )
    }
}