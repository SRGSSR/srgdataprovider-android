package ch.srg.dataProvider.integrationlayer.data.search

import ch.srg.dataProvider.integrationlayer.data.SRGIdentifierMetadata
import ch.srg.dataProvider.integrationlayer.data.Vendor

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class SearchResult(
        override val id: String,
        override val vendor: Vendor,
        override val urn: String
) : SRGIdentifierMetadata