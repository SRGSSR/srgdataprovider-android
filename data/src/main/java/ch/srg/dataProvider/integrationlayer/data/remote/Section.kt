package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class Section(
    override val id: String,
    override val vendor: Vendor,
    @Json(name = "sectionType")
    val type: String,
    val representation: Representation,
    val isPublished: Boolean,
    @Json(name = "start")
    val startDate: Date? = null,
    @Json(name = "end")
    val endDate: Date? = null,
    val hasPersonalizedContent: Boolean? = null,
) : ILObject
