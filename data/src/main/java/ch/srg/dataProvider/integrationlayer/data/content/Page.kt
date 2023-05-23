package ch.srg.dataProvider.integrationlayer.data.content

import ch.srg.dataProvider.integrationlayer.data.ILObject
import ch.srg.dataProvider.integrationlayer.data.Vendor

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Page(
        override val id: String,
        override val vendor: Vendor,
        val title: String,
        val isPublished: Boolean,
        val sectionList: List<Section>? = null,
        val topicUrn: String? = null
) : ILObject {
}