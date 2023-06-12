package ch.srg.dataProvider.integrationlayer.data

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class TimeInterval(val type: Type, val markIn: Long, val markOut: Long) {
    enum class Type {
        OPENING_CREDITS, CLOSING_CREDITS
    }
}
