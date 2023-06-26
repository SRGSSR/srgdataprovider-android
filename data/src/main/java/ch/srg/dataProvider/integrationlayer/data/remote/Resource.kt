package ch.srg.dataProvider.integrationlayer.data.remote

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class Resource @JvmOverloads constructor(
    val url: String,
    val drmList: List<Drm>? = null,
    val drm: Boolean = false,
    val quality: Quality,
    val mimeType: String? = null,
    val presentation: Presentation = Presentation.DEFAULT,
    @Json(name = "streaming")
    val streamingMethod: StreamingMethod,
    val dvr: Boolean = false,
    val live: Boolean = false,
    val mediaContainer: MediaContainer = MediaContainer.UNKNOWN,
    val audioCodec: AudioCodec = AudioCodec.UNKNOWN,
    val videoCodec: VideoCodec = VideoCodec.UNKNOWN,
    val tokenType: TokenType = TokenType.NONE,
    @Json(name = "subtitleInformationList")
    val subtitleVariants: List<Variant>? = null,
    @Json(name = "audioTrackList")
    val audioVariants: List<Variant>? = null,
    /**
     * The stream offset from the real-world clock, in milliseconds (0 for on-demand or streams without DVR capabilities).
     */
    @Json(name = "streamOffset")
    val windowDvrOffset: Long? = null,
    @Json(name = "analyticsData")
    var comScoreAnalyticsLabels: HashMap<String, String>? = null,
    @Json(name = "analyticsMetadata")
    var analyticsLabels: HashMap<String, String>? = null,
) {
    val streamType: StreamType
        get() {
            return when {
                dvr -> StreamType.LIVE_WITH_DVR
                live -> StreamType.LIVE
                else -> StreamType.ON_DEMAND
            }
        }

    fun isLocalFile(): Boolean {
        return Uri.parse(url).scheme == LOCAL_FILE_SCHEME_URL
    }

    fun hasDrm(): Boolean {
        return drmList != null && drmList.isNotEmpty()
    }

    fun hasSubtitles(): Boolean {
        return subtitleVariants != null && subtitleVariants.isNotEmpty()
    }

    fun hasAudioTracks(): Boolean {
        return audioVariants != null && audioVariants.isNotEmpty()
    }

    data class Drm(val type: Type, val licenseUrl: String, val certificateUrl: String?) {
        enum class Type {
            FAIRPLAY, WIDEVINE, PLAYREADY
        }
    }

    companion object {
        const val LOCAL_FILE_SCHEME_URL = "file"
    }
}
