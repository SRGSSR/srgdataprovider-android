package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
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
    @Json(name = "subtitleInformationList")
    val subtitleVariants: List<Variant>? = null,
    @Json(name = "audioTrackList")
    val audioVariants: List<Variant>? = null
) : SRGMediaMetadata
