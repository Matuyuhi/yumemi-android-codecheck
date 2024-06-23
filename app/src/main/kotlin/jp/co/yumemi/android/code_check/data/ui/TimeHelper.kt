package jp.co.yumemi.android.code_check.data.ui

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.toDisplayFormattedString(): String {
    return "${this.year}-${this.monthNumber}-${this.dayOfMonth} ${this.hour}:${this.minute}:${this.second}"
}

fun String.toLocalDateTimeWithTimeZone(): LocalDateTime {
    try {
        val instant = Instant.parse(this)
        val dateTime = instant.toLocalDateTime(TimeZone.UTC)
        return dateTime
    } catch (e: Exception) {
        throw IllegalArgumentException("Invalid date time format, $this")
    }
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.UTC)
}

fun String?.replaceIfEmpty(): String {
    return this ?: "--:--:--"
}