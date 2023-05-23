package ch.srg.dataProvider.integrationlayer.data.content

import ch.srg.dataProvider.integrationlayer.data.ILObject
import ch.srg.dataProvider.integrationlayer.data.Vendor
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Section(
        override val id: String,
        override val vendor: Vendor,
        @SerializedName("sectionType")
        val type: String,
        val representation: Representation,
        val isPublished: Boolean,
        @SerializedName("start")
        val startDate: Date? = null,
        @SerializedName("end")
        val endDate: Date? = null,
        val hasPersonalizedContent: Boolean? = null,
) : ILObject