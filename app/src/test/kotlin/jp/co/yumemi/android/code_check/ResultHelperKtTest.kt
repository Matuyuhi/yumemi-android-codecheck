package jp.co.yumemi.android.code_check

import jp.co.yumemi.android.code_check.screen.result.formatDigits
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultScreenHelperKtTest {

    @Test
    fun `test String_formatDigits`() {
        assertEquals("  12", "12".formatDigits())
        assertEquals("1234", "1234".formatDigits())
        assertEquals("12345", "12345".formatDigits())  // 元のコードではこのケースは考慮されていませんが、確認のために追加
    }

    @Test
    fun `test Int_formatDigits for small numbers`() {
        assertEquals("  12", 12.formatDigits())
        assertEquals(" 999", 999.formatDigits())
    }

    @Test
    fun `test Int_formatDigits for thousands`() {
        assertEquals("1.0K", 1000.formatDigits())
        assertEquals("1.2K", 1200.formatDigits())
        assertEquals("9.9K", 9900.formatDigits())
    }

    @Test
    fun `test Int_formatDigits for ten thousands and above`() {
        assertEquals("1.0M", 10000.formatDigits())
        assertEquals("1.2M", 12000.formatDigits())
        assertEquals("9.9M", 99000.formatDigits())
    }
}