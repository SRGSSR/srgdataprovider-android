package ch.srg.dataProvider.integrationlayer.data.remote

import java.util.*

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */

sealed interface Bucket {
    val count: Int
}

data class MediaTypeBucket(val mediaType: MediaType, override val count: Int) : Bucket
data class SubtitlesAvailableBucket(val subtitlesAvailable: Boolean, override val count: Int) : Bucket

data class DownloadAvailableBucket(val downloadAvailable: Boolean, override val count: Int) : Bucket
data class PlayableAbroadBucket(val playableAbroad: Boolean, override val count: Int) : Bucket
data class QualityBucket(val quality: Quality, override val count: Int) : Bucket
data class TopicBucket(val urn: String, val title: String, override val count: Int) : Bucket
data class ShowBucket(val urn: String, val title: String, override val count: Int) : Bucket
data class DurationInMinutesBucket(val duration: Long, override val count: Int) : Bucket
data class DateBucket(val date: Date, override val count: Int) : Bucket

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
