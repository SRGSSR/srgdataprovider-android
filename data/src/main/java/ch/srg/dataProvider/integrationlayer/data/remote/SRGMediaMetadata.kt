package ch.srg.dataProvider.integrationlayer.data.remote

import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
interface SRGMediaMetadata : SRGIdentifierMetadata, SRGImageMetadata, SRGMetadata {
    val mediaType: MediaType
    val type: Type
    val date: Date
    val duration: Long
    val blockReason: BlockReason?
    val validFrom: Date?
    val validTo: Date?
    val assignedBy: Referrer?
    val playableAbroad: Boolean
    val youthProtectionColor: YouthProtectionColor?
    val podcastSdUrl: String?
    val podcastHdUrl: String?
    val relatedContentList: List<RelatedContent>?
    val socialCountList: List<SocialCountEntry>?
    val aspectRatio: AspectRatio?

    fun getDownloadUri(quality: Quality = Quality.HD): String? {
        return when (quality) {
            Quality.SD -> podcastSdUrl
            Quality.HD, Quality.HQ -> if (podcastHdUrl.isNullOrEmpty()) podcastSdUrl else podcastHdUrl
        }
    }

    /**
     * isBlocked if it has a blockReason or blocked by TimeAvailability at a given time
     */
    fun isBlocked(at: Date = Date()): Boolean {
        return blockReason != null || getTimeAvailability(at) != TimeAvailability.AVAILABLE
    }

    fun getTimeAvailability(at: Date = Date()): TimeAvailability {
        return when {
            blockReason == BlockReason.STARTDATE -> TimeAvailability.NOT_YET_AVAILABLE
            blockReason == BlockReason.ENDDATE -> TimeAvailability.NOT_AVAILABLE_ANYMORE
            validTo != null && at.after(validTo) -> TimeAvailability.NOT_AVAILABLE_ANYMORE
            validFrom != null && at.before(validFrom) -> TimeAvailability.NOT_YET_AVAILABLE
            else -> TimeAvailability.AVAILABLE
        }
    }

    fun isBlockedValidFromTime(currentTime: Long = System.currentTimeMillis()): Boolean {
        return validFrom != null && validFrom!!.time > currentTime
    }

    fun isBlockValidToTime(currentTime: Long = System.currentTimeMillis()): Boolean {
        return validTo != null && currentTime > validTo!!.time
    }

    fun isTimeBlocked(currentTime: Long): Boolean {
        return isBlockedValidFromTime(currentTime) || isBlockValidToTime()
    }

    fun isLive(): Boolean {
        return type == Type.SCHEDULED_LIVESTREAM || type == Type.LIVESTREAM
    }
}
