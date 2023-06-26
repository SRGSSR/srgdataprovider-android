package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class Subtitle(
    override val locale: String,
    override val source: VariantSource,
    val url: String,
    val format: SubtitleFormat,
    override val language: String? = null,
    override val type: VariantType? = null,
) : SRGVariant
