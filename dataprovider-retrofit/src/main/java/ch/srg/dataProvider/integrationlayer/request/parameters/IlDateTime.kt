package ch.srg.dataProvider.integrationlayer.request.parameters

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Format date for Integration layer that use Format: ‘yyyy-MM-ddTHH:mm:ss’ or ISO-8601 based format.
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class IlDateTime(date: Date = Date()) : IlParam(SimpleDateFormat("yyyy-MM-ddTHH:mm:ss", Locale.US).format(date)) {
    constructor(calendar: Calendar) : this(calendar.time)
    constructor(time: Long) : this(Date(time))
}
