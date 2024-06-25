package jp.co.yumemi.android.code_check

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import jp.co.yumemi.android.code_check.mock.mockResultScreenArgsModel
import jp.co.yumemi.android.code_check.screen.TestTags
import jp.co.yumemi.android.code_check.screen.result.ResultScreen
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class ResultFragmentTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `リンクがなければ押せない`() {
        composeTestRule.setContent {
            ResultScreen(
                model = mockResultScreenArgsModel.copy(url = "")
            )
        }
        composeTestRule.onNodeWithTag(TestTags.ResultScreenUrlOpenButton.name).assertIsNotEnabled()
    }

    @Test
    fun `リンクがあれば押せる`() {
        composeTestRule.setContent {
            ResultScreen(
                model = mockResultScreenArgsModel.copy(url = "https://www.google.com")
            )
        }
        composeTestRule.onNodeWithTag(TestTags.ResultScreenUrlOpenButton.name).assertIsEnabled()
    }

}