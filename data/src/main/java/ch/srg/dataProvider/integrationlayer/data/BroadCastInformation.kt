package ch.srg.dataProvider.integrationlayer.data

import java.util.*

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class BroadCastInformation @JvmOverloads constructor(
    val hintText: String? = null,
    val url: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null
)
