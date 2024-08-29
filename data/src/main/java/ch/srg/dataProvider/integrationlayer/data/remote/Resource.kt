package ch.srg.dataProvider.integrationlayer.data.remote

import android.net.Uri
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Resource @JvmOverloads constructor(
    val url: String,
    val drmList: List<Drm>? = null,
    val drm: Boolean = false,
    val quality: Quality,
    val mimeType: String? = null,
    val presentation: Presentation = Presentation.DEFAULT,
    @SerialName("streaming")
    val streamingMethod: StreamingMethod,
    val dvr: Boolean = false,
    val live: Boolean = false,
    val mediaContainer: MediaContainer = MediaContainer.UNKNOWN,
    val audioCodec: AudioCodec = AudioCodec.UNKNOWN,
    val videoCodec: VideoCodec = VideoCodec.UNKNOWN,
    val tokenType: TokenType = TokenType.NONE,
    @SerialName("subtitleInformationList")
    val subtitleVariants: List<Variant>? = null,
    @SerialName("audioTrackList")
    val audioVariants: List<Variant>? = null,
    /**
     * The stream offset from the real-world clock, in milliseconds (0 for on-demand or streams without DVR capabilities).
     */
    @SerialName("streamOffset")
    val windowDvrOffset: Long? = null,
    @SerialName("analyticsData")
    var comScoreAnalyticsLabels: HashMap<String, String>? = null,
    @SerialName("analyticsMetadata")
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
        return !drmList.isNullOrEmpty()
    }

    fun hasSubtitles(): Boolean {
        return !subtitleVariants.isNullOrEmpty()
    }

    fun hasAudioTracks(): Boolean {
        return !audioVariants.isNullOrEmpty()
    }

    @Serializable
    data class Drm(val type: Type, val licenseUrl: String, val certificateUrl: String?) {
        enum class Type {
            FAIRPLAY, WIDEVINE, PLAYREADY
        }
    }

    companion object {
        const val LOCAL_FILE_SCHEME_URL = "file"
    }
}
