package ch.srg.dataProvider.integrationlayer.data


/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class MediaStatisticResult(
        override val id: String,
        override val vendor: Vendor,
        override val urn: String,
        val mediaType: MediaType,
        val socialCountList: List<SocialCountEntry>? = null) : SRGIdentifierMetadata

data class MediaStatisticPost(val eventData: String, val userId: String?)
