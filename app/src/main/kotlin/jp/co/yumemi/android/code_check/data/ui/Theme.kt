package jp.co.yumemi.android.code_check.data.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val Black = Color(0xFF111111)
val ScrimLight = Color(0x66000000)
val ScrimMedium = Color(0x99000000)
val ScrimBlack = Color(0xCC000000)

@Composable
fun AppTheme(content: @Composable () -> Unit) {

    MaterialTheme() {
        content()
    }
}