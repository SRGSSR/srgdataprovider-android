package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class TimeInterval(val type: Type, val markIn: Long, val markOut: Long) {

    enum class Type {
        OPENING_CREDITS, CLOSING_CREDITS
    }
}
