@file:UseSerializers(DateSerializer::class)

package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Episode(
    val id: String,
    override val title: String,
    override val imageUrl: String,
    override val lead: String? = null,
    override val description: String? = null,
    override val imageTitle: String? = null,
    override val imageCopyright: String? = null,
    val publishedDate: Date? = null,
    val fullLengthUrn: String? = null,
    val seasonNumber: Int? = null,
    val number: Int? = null,
    val socialCount: List<SocialCountEntry>? = null,
    val mediaList: List<Media>? = null,
    override val imageFocalPoint: FocalPoint? = null
) : SRGMetadata, SRGImageMetadata
