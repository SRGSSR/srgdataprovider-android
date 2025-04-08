@file:UseSerializers(DateSerializer::class)

package ch.srg.dataProvider.integrationlayer.data.remote

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
data class Segment @JvmOverloads constructor(
    override val id: String,
    override val mediaType: MediaType,
    override val vendor: Vendor,
    override val urn: String,
    override val title: String,
    /**
     *  The time at which the segment starts, in milliseconds.
     */
    val markIn: Long,
    /**
     *  The time at which the segment ends, in milliseconds.
     */
    val markOut: Long,
    override val type: Type,
    override val date: Date,
    override val duration: Long,
    override val displayable: Boolean,
    override val playableAbroad: Boolean,
    override val lead: String? = null,
    override val description: String? = null,
    override val imageUrl: String,
    override val imageFocalPoint: FocalPoint? = null,
    override val imageTitle: String? = null,
    override val imageCopyright: String? = null,
    override val blockReason: BlockReason? = null,
    override val youthProtectionColor: YouthProtectionColor? = null,
    override val podcastSdUrl: String? = null,
    override val podcastHdUrl: String? = null,
    override val validFrom: Date? = null,
    override val validTo: Date? = null,
    override val assignedBy: Referrer? = null,
    override val relatedContentList: List<RelatedContent>? = null,
    override val socialCountList: List<SocialCountEntry>? = null,
    override val fullLengthUrn: String? = null,
    override val eventData: String? = null,
    override val subtitleList: List<Subtitle>? = null,
    @SerialName("analyticsData")
    override var comScoreAnalyticsLabels: HashMap<String, String>? = null,
    @SerialName("analyticsMetadata")
    override var analyticsLabels: HashMap<String, String>? = null,
    /**
     * Set by the chapter
     */
    override var aspectRatio: AspectRatio? = null
) : SRGSubDivision {
    /**
     *  The date corresponding to the mark in time, `null` if no such relationship exists.
     *  @see resourceReferenceDate
     */
    var markInDate: Date? = null

    /**
     * The date corresponding to the mark out time, `null` if no such relationship exists.
     *  @see resourceReferenceDate
     */
    var markOutDate: Date? = null

    /**
     * Reference date to compute mark in and mark out date, `null` if no such relationship exists.
     */
    var resourceReferenceDate: Date? = null
        set(value) {
            field = value
            if (value != null) {
                markInDate = Date(value.time + markIn)
                markOutDate = Date(value.time + markOut)
            } else {
                markInDate = null
                markOutDate = null
            }
        }
}
