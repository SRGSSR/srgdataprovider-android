package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class SearchResult(
    override val id: String,
    override val vendor: Vendor,
    override val urn: String
) : SRGIdentifierMetadata
