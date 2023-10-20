package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class MediaStatisticResult(
    override val id: String,
    override val vendor: Vendor,
    override val urn: String,
    val mediaType: MediaType,
    val socialCountList: List<SocialCountEntry>? = null
) : SRGIdentifierMetadata

@Serializable
data class ShowStatisticResult(
    override val id: String,
    override val vendor: Vendor,
    override val urn: String,
    val transmission: Transmission,
    val searchResultClicked: Int = 0,
) : SRGIdentifierMetadata

@Serializable
data class MediaStatisticPost(val eventData: String, val userId: String? = null)
