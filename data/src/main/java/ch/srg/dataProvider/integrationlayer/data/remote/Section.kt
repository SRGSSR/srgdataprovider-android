package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Section(
    override val id: String,
    override val vendor: Vendor,
    @SerialName("sectionType")
    val type: String,
    val representation: Representation,
    val isPublished: Boolean,
    @SerialName("start")
    val startDate: Instant? = null,
    @SerialName("end")
    val endDate: Instant? = null,
    val hasPersonalizedContent: Boolean? = null,
    val mediaType: MediaType? = null,
) : ILObject
