package ch.srg.dataProvider.integrationlayer.data

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class RelatedContent(
    val id: String,
    override val title: String,
    override val lead: String? = null,
    override val description: String? = null,
    val url: String,
    val contentType: String? = null,
    val isExternal: Boolean = false
) : SRGMetadata
