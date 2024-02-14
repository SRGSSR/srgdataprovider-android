package ch.srg.dataProvider.integrationlayer.data.remote
import kotlinx.serialization.Serializable
/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Page(
    override val id: String,
    override val vendor: Vendor,
    val title: String,
    val isPublished: Boolean,
    val sectionList: List<Section>? = null,
    val topicUrn: String? = null,
    val description: String? = null,
    val type: String? = null,
) : ILObject
