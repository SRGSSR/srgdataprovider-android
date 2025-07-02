package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable

data class BroadCastInformation(
    val hintText: String? = null,
    val url: String? = null,
    val startDate: Instant? = null,
    val endDate: Instant? = null
)
