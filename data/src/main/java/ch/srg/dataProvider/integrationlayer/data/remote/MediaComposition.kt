package ch.srg.dataProvider.integrationlayer.data.remote

import android.text.TextUtils
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *  Full information used when playing a media. A media composition provides the full playback context:
 *    - List of chapters and segments, and which one should be played first.
 *    - Complete media information.
 *    - Analytics information.
 *
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class MediaComposition(
    val chapterUrn: String,
    val chapterList: List<Chapter>,
    val segmentUrn: String? = null,
    val episode: Episode? = null,
    val show: Show? = null,
    val channel: Channel? = null,
    @SerialName("analyticsData")
    val comScoreAnalyticsLabels: HashMap<String, String>? = null,
    @SerialName("analyticsMetadata")
    val analyticsLabels: HashMap<String, String>? = null,
) {

    constructor(media: SRGMediaMetadata) : this(
        chapterUrn = media.urn,
        chapterList = listOf(Chapter(media)),
        segmentUrn = null
    )

    /**
     *  The chapter which should be initially played.
     */
    fun getMainChapter(): Chapter {
        return checkNotNull(findChapter(chapterUrn)) { "The main chapter is missing from mediaComposition" }
    }

    /**
     *  The segment from the main chapter which should be initially played, if any.
     */
    fun findMainSegment(): Segment? {
        return segmentUrn?.let { findSegment(chapterUrn, it) }
    }

    fun findChapter(urn: String): Chapter? {
        return chapterList.find { it.urn == urn }
    }

    fun findSegment(chapterUrn: String, segmentUrn: String): Segment? {
        return findChapter(chapterUrn)?.findSegment(segmentUrn)
    }

    /**
     * @return the 360 video Resource or null if no 360 video in resource list
     */
    fun find360Resource(): Resource? {
        return getMainChapter().find360Resource()
    }

    /**
     * Search in ChapterList and Segment list for urn
     */
    fun containsUrn(urn: String): Boolean {
        for (chapter in chapterList) {
            if (chapter.urn == urn || chapter.findSegment(urn) != null) {
                return true
            }
        }
        return false
    }

    fun containsChapterOrSegment(urn: String): Boolean {
        if (chapterList.isEmpty()) {
            return false
        }
        for (chapter in chapterList) {
            if (TextUtils.equals(chapter.urn, urn)) {
                return true
            }
            if (chapter.findSegment(urn) != null) {
                return true
            }
        }
        return false
    }
}
