package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.JsonClass
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class BroadCastInformation(
    val hintText: String? = null,
    val url: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null
)
