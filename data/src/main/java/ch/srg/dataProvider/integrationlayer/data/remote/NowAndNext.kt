package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class NowAndNext(
    override val id: String,
    override val vendor: Vendor,
    override val urn: String,
    override val title: String,
    override val lead: String? = null,
    override val description: String? = null,
    override val imageUrl: ImageUrl,
    override val imageFocalPoint: FocalPoint? = null,
    override val imageTitle: String? = null,
    override val imageCopyright: String? = null,
    override val transmission: Transmission,
    override val timeTableUrl: String? = null,
    @Json(name = "imageUrlRaw")
    override val rawImageUrl: ImageUrl? = null,
    val now: Program? = null,
    val next: Program? = null
) :
    SRGChannelMetadata
