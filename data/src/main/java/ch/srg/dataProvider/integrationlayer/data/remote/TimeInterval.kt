package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class TimeInterval(val type: Type, val markIn: Long, val markOut: Long) {

    enum class Type {
        OPENING_CREDITS, CLOSING_CREDITS
    }
}
