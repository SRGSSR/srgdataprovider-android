package ch.srg.dataProvider.integrationlayer.data

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * DateParser to convert Integration Layer String date to date (ISO_8601)
 *
 * DateParser isn't Thread Safe, each thread must use its own instance.
 * Because SimpleDateFormat isn't Thread Safe @link(https://developer.android.com/reference/java/text/SimpleDateFormat)
 *
 *
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
class ISO8601DateParser {
    // SimpleDateFormat use Object that aren't thread-safe, what may lead to IndexOutOfBoundException.
    private val sdf: SimpleDateFormat = SimpleDateFormat(ISO_8601_FORMAT, Locale.US)

    @Throws(ParseException::class)
    fun parseDate(s: String): Date {
        val fixed = fix8601ForSimpleDateFormat(s)
        return sdf.parse(fixed) ?: throw ParseException("Can't parse $s", 0)
    }

    fun format(date: Date): String {
        return sdf.format(date)
    }

    companion object {
        const val ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"

        fun fix8601ForSimpleDateFormat(s: String): String {
            var output = s
            if (output.endsWith("Z")) {
                output = output.substring(0, output.length - 1) + "+00:00"
            }
            output = output.replace("\\.\\d+".toRegex(), "")
            return output
        }
    }
}
