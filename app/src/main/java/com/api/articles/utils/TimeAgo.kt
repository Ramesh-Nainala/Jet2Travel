package com.api.articles.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeAgo {


    fun getTimeAgo(date: String): String {

        var time = convertDateToLong(date, DATE_FORMAT)

        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return ""
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> JUST_NOW
            diff < 2 * MINUTE_MILLIS -> "1".plus(MIN_PREFIX)
            diff < 50 * MINUTE_MILLIS -> (diff / MINUTE_MILLIS).toString().plus(MIN_PREFIX).plus("s")
            diff < 90 * MINUTE_MILLIS -> "1 ".plus(HR_PREFIX)
            diff < 24 * HOUR_MILLIS -> (diff / HOUR_MILLIS).toString().plus(HR_PREFIX).plus("s")
            diff < 48 * HOUR_MILLIS -> YESTERDAY_PREFIX
            else -> convertLongToDate(time, REQUIRED_FORMAT)
        }
    }


    private fun convertDateToLong(date: String, dateFormat: String): Long {
        val df = SimpleDateFormat(dateFormat, Locale.getDefault())
        return df.parse(date).time
    }

    private fun convertLongToDate(millisecond: Long, format: String): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(Date(millisecond))
    }


    companion object {
        private const val JUST_NOW = "Just now"
        private const val MIN_PREFIX: String = "min"
        private const val HR_PREFIX: String = "hr"
        private const val YESTERDAY_PREFIX = "yesterday"
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val REQUIRED_FORMAT = "dd, MMM yyyy HH:mm a"
        private const val SECOND_MILLIS: Int = 1000
        private const val MINUTE_MILLIS: Int = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS: Int = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS: Int = 24 * HOUR_MILLIS
    }
}