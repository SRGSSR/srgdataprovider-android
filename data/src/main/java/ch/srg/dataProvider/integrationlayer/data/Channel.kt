package ch.srg.dataProvider.integrationlayer.data

import com.google.gson.annotations.SerializedName

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Channel(
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
        @SerializedName("imageUrlRaw")
        override val rawImageUrl: ImageUrl? = null
) : SRGChannelMetadata