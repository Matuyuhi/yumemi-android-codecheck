package jp.co.yumemi.android.code_check

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import jp.co.yumemi.android.code_check.screen.TestTags
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest


class SearchFragmentTest : KoinTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
    }

    @Test
    fun `文字を入力した後検索ボタンを押せる`() {
        // Simulate text input in search field
        composeTestRule.onNodeWithTag(TestTags.SearchScreenInputField.name).performTextInput("Kotlin")

        // Simulate a button click for searching
        composeTestRule.onNodeWithTag(TestTags.SearchScreenSubmitButton.name).assertIsEnabled()
    }

    @Test
    fun `文字の入力がない時は押せない`() {
        composeTestRule.onNodeWithTag(TestTags.SearchScreenInputField.name).performTextInput("")
        composeTestRule.onNodeWithTag(TestTags.SearchScreenSubmitButton.name).assertIsNotEnabled()
    }

    @After
    fun tearDown() {
    }
}