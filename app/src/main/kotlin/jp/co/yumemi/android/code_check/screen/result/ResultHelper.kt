package jp.co.yumemi.android.code_check.screen.result


private fun String.formatDigits(): String {
    return padStart(4, ' ')
}

fun Int.formatDigits(): String {
    return toAboutCountString(this).formatDigits()
}

private fun toAboutCountString(count: Int): String {
    return when {
        count < 1000 -> count.toString()
        count < 10000 -> "${count / 1000}.${count % 1000 / 100}K"
        else -> "${count / 10000}.${count % 10000 / 1000}M"
    }
}