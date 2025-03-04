package ch.srg.dataProvider.integrationlayer.request.parameters

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Write date as YYYY-MM-dd
 *
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class IlDate(date: Date = Date()) : IlParam(formatDate(date)) {
    constructor(calendar: Calendar) : this(calendar.time)

    constructor(instant: Instant = Clock.System.now()) : this(Date(instant.toEpochMilliseconds()))

    /**
     * months = [0-11]
     * days = [0-30]
     */
    constructor(years: Int, months: Int = 0, days: Int = 0) : this(
        Calendar.getInstance(zurichTimeZone).apply {
            set(Calendar.YEAR, years)
            set(years, months, days)
        }
    )

    companion object {
        val zurichTimeZone: TimeZone = TimeZone.getTimeZone("GMT+2")

        private fun formatDate(date: Date): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            return sdf.format(date)
        }
    }
}
