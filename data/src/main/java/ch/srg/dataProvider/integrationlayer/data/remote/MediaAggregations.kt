package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */

sealed interface Bucket {
    val count: Int
}

@Serializable
data class MediaTypeBucket(val mediaType: MediaType, override val count: Int) : Bucket

@Serializable
data class SubtitlesAvailableBucket(val subtitlesAvailable: Boolean, override val count: Int) : Bucket

@Serializable
data class DownloadAvailableBucket(val downloadAvailable: Boolean, override val count: Int) : Bucket

@Serializable
data class PlayableAbroadBucket(val playableAbroad: Boolean, override val count: Int) : Bucket

@Serializable
data class QualityBucket(val quality: Quality, override val count: Int) : Bucket

@Serializable
data class TopicBucket(val urn: String, val title: String, override val count: Int) : Bucket

@Serializable
data class ShowBucket(val urn: String, val title: String, override val count: Int) : Bucket

@Serializable
data class DurationInMinutesBucket(val duration: Long, override val count: Int) : Bucket

@Serializable
data class DateBucket(val date: Instant, override val count: Int) : Bucket

@Serializable
data class MediaAggregations(
    val mediaTypeList: List<MediaTypeBucket>? = null,
    val subtitlesAvailableList: List<SubtitlesAvailableBucket>? = null,
    val downloadAvailableList: List<DownloadAvailableBucket>? = null,
    val playableAbroadList: List<PlayableAbroadBucket>? = null,
    val qualityList: List<QualityBucket>? = null,
    val topicList: List<TopicBucket>? = null,
    val showList: List<ShowBucket>? = null,
    val durationListInMinutes: List<DurationInMinutesBucket>? = null,
    val dataList: List<DateBucket>? = null
)
