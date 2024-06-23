package jp.co.yumemi.android.code_check

import jp.co.yumemi.android.code_check.data.ui.replaceIfEmpty
import jp.co.yumemi.android.code_check.data.ui.toDisplayFormattedString
import jp.co.yumemi.android.code_check.data.ui.toLocalDateTime
import jp.co.yumemi.android.code_check.data.ui.toLocalDateTimeWithTimeZone
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class TimeHelperKtTest {

    @Test
    fun `test LocalDateTime_toDisplayFormattedString`() {
        val dateTime = LocalDateTime(2023, 6, 23, 15, 30, 45)
        val expected = "2023-6-23 15:30:45"
        assertEquals(expected, dateTime.toDisplayFormattedString())
    }

    @Test
    fun `test String_toLocalDateTimeWithTimeZone_valid`() {
        val dateString = "2023-06-23T15:30:45Z"
        val expected = LocalDateTime(2023, 6, 23, 15, 30, 45).also {
            assertEquals(it, dateString.toLocalDateTimeWithTimeZone())
        }
    }

    @Test
    fun `test String_toLocalDateTimeWithTimeZone_invalid`() {
        val dateString = "invalid-date"
        val exception = assertThrows(IllegalArgumentException::class.java) {
            dateString.toLocalDateTimeWithTimeZone()
        }
        assertEquals("Invalid date time format, invalid-date", exception.message)
    }

    @Test
    fun `test Long_toLocalDateTime`() {
        val epochMillis = 1687534245000L // 2023-06-23T15:30:45Z
        val expected = LocalDateTime(2023, 6, 23, 15, 30, 45)
        assertEquals(expected, epochMillis.toLocalDateTime())
    }

    @Test
    fun `test String_replaceIfEmpty_non_null`() {
        val str = "12:34:56"
        assertEquals("12:34:56", str.replaceIfEmpty())
    }

    @Test
    fun `test String_replaceIfEmpty_null`() {
        val str: String? = null
        assertEquals("--:--:--", str.replaceIfEmpty())
    }
}