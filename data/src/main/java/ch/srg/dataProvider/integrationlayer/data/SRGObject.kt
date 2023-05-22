package ch.srg.dataProvider.integrationlayer.data

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
interface ILObject {
    val id: String
    val vendor: Vendor
}

interface SRGIdentifierMetadata : ILObject {
    val urn: String
}

interface SRGMetadata {
    val title: String
    val lead: String?
    val description: String?
}

interface SRGImageMetadata {
    val imageUrl: ImageUrl
    val imageTitle: String?
    val imageCopyright: String?
    val imageFocalPoint: FocalPoint?
}

interface SRGChannelMetadata : SRGIdentifierMetadata, SRGMetadata, SRGImageMetadata {
    val transmission: Transmission
    val timeTableUrl: String?
    val rawImageUrl: ImageUrl?
}
