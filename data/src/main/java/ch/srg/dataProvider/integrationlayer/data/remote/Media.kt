@file:UseSerializers(DateSerializer::class)
package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Media(
    override val id: String,
    override val mediaType: MediaType,
    override val vendor: Vendor,
    override val urn: String,
    override val title: String,
    override val type: Type,
    override val date: Date,
    override val duration: Long,
    override val imageUrl: ImageUrl,
    override val imageFocalPoint: FocalPoint? = null,
    override val lead: String? = null,
    override val description: String? = null,
    override val imageTitle: String? = null,
    override val imageCopyright: String? = null,
    override val blockReason: BlockReason? = null,
    override val youthProtectionColor: YouthProtectionColor? = null,
    override val podcastSdUrl: String? = null,
    override val podcastHdUrl: String? = null,
    override val validFrom: Date? = null,
    override val validTo: Date? = null,
    override val assignedBy: Referrer? = null,
    override val playableAbroad: Boolean,
    override val relatedContentList: List<RelatedContent>? = null,
    override val socialCountList: List<SocialCountEntry>? = null,
    override val aspectRatio: AspectRatio? = null,
    val presentation: Presentation? = Presentation.DEFAULT,
    val show: Show? = null,
    val channel: Channel? = null,
    val episode: Episode? = null,
    @SerialName("subtitleInformationList")
    val subtitleVariants: List<Variant>? = null,
    @SerialName("audioTrackList")
    val audioVariants: List<Variant>? = null
) : SRGMediaMetadata
