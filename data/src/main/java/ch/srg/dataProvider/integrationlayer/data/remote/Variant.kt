package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
interface SRGVariant : SRGLanguage {
    val source: VariantSource
    val type: VariantType?
}

@Serializable
data class Variant(
    override val locale: String,
    override val source: VariantSource,
    override val language: String? = null,
    override val type: VariantType? = null
) : SRGVariant
