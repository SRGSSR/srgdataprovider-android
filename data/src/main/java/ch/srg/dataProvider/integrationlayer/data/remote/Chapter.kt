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
data class Chapter(
    override val id: String,
    override val mediaType: MediaType,
    override val vendor: Vendor,
    override val urn: String,
    override val title: String,
    override val lead: String? = null,
    override val description: String? = null,
    override val imageUrl: ImageUrl,
    override val imageTitle: String? = null,
    override val imageCopyright: String? = null,
    override val blockReason: BlockReason? = null,
    override val youthProtectionColor: YouthProtectionColor? = null,
    override val type: Type,
    override val date: Date,
    override val duration: Long,
    override val podcastSdUrl: String? = null,
    override val podcastHdUrl: String? = null,
    override val validFrom: Date? = null,
    override val validTo: Date? = null,
    override val assignedBy: Referrer? = null,
    override val playableAbroad: Boolean = false,
    override val relatedContentList: List<RelatedContent>? = null,
    override val socialCountList: List<SocialCountEntry>? = null,
    override val displayable: Boolean = false,
    override val fullLengthUrn: String? = null,
    override val eventData: String? = null,
    override val subtitleList: List<Subtitle>? = null,
    @SerialName("analyticsData")
    override val comScoreAnalyticsLabels: HashMap<String, String>? = null,
    @SerialName("analyticsMetadata")
    override val analyticsLabels: HashMap<String, String>? = null,
    override val aspectRatio: AspectRatio? = null,
    val segmentList: List<Segment>? = null,
    val resourceList: List<Resource>? = null,
    val spriteSheet: SpriteSheet? = null,
    val preTrailerStart: Date? = null,
    val postTrailerStart: Date? = null,
    /**
     *  The reference date corresponding to the beginning of the stream, if any. You can use this date to map a time
     *  position relative to the stream (e.g. a segment mark in or mark out) to a date.
     */
    @SerialName("dvrReferenceDate")
    val resourceReferenceDate: Date? = null,
    val timeIntervalList: List<TimeInterval>? = null,
    override val imageFocalPoint: FocalPoint? = null

) : SRGSubDivision {
    init {
        if (segmentList != null) {
            for (segment in segmentList) {
                segment.aspectRatio = aspectRatio
                segment.resourceReferenceDate = resourceReferenceDate
            }
        }
    }

    constructor(media: SRGMediaMetadata) : this(
        id = media.id,
        mediaType = media.mediaType,
        vendor = media.vendor,
        urn = media.urn,
        title = media.title,
        lead = media.lead,
        description = media.description,
        imageUrl = media.imageUrl,
        imageTitle = media.imageTitle,
        imageCopyright = media.imageTitle,
        blockReason = media.blockReason,
        youthProtectionColor = media.youthProtectionColor,
        type = media.type,
        date = media.date,
        duration = media.duration,
        podcastSdUrl = media.podcastSdUrl,
        podcastHdUrl = media.podcastHdUrl,
        validFrom = media.validFrom,
        validTo = media.validTo,
        assignedBy = media.assignedBy,
        playableAbroad = media.playableAbroad,
        relatedContentList = media.relatedContentList,
        socialCountList = media.socialCountList,
        displayable = true
    )

    fun findSegment(urn: String): Segment? {
        return segmentList?.find { it.urn == urn }
    }

    fun find360Resource(): Resource? {
        return resourceList?.find { it.presentation == Presentation.VIDEO_360 }
    }

    fun is360(): Boolean {
        return find360Resource() != null
    }

    fun doesHaveBlockedSegment(): Boolean {
        for (segment in segmentList.orEmpty()) {
            if (segment.isBlocked()) {
                return true
            }
        }
        return false
    }
}
