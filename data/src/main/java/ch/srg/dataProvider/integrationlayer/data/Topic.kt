package ch.srg.dataProvider.integrationlayer.data

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Topic(
        override val id: String,
        override val vendor: Vendor,
        val transmission: Transmission,
        override val urn: String,
        override val title: String,
        override val lead: String? = null,
        override val description: String? = null,
        val viewedMedias: Int? = null,
        val imageUrl: ImageUrl? = null,
        val imageTitle: String? = null,
        val imageFocalPoint: FocalPoint? = null,
        val imageCopyright: String? = null,
        val subTopicList: List<Topic>? = null
) : SRGIdentifierMetadata, SRGMetadata {
}