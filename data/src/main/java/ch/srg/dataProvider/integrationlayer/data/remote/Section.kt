@file:UseSerializers(DateSerializer::class)

package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.Date

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
    val startDate: Date? = null,
    @SerialName("end")
    val endDate: Date? = null,
    val hasPersonalizedContent: Boolean? = null,
) : ILObject
