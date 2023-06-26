package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class Page(
    override val id: String,
    override val vendor: Vendor,
    val title: String,
    val isPublished: Boolean,
    val sectionList: List<Section>? = null,
    val topicUrn: String? = null
) : ILObject
