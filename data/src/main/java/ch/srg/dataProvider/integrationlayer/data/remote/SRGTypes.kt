package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.Json

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
enum class Vendor {
    SRF, RTR, RTS, RSI, SWI, SSATR, TVO, CA
}

enum class Quality {
    SD, HD, HQ
}

enum class Transmission {
    TV, RADIO, ONLINE
}

enum class MediaType {
    VIDEO, AUDIO
}

enum class Type {
    EPISODE, EXTRACT, TRAILER, CLIP, LIVESTREAM, SCHEDULED_LIVESTREAM
}

enum class LiveCenterType {
    EPISODE, SCHEDULED_LIVESTREAM
}

enum class YouthProtectionColor {
    RED, YELLOW
}

enum class BlockReason {
    GEOBLOCK, LEGAL, COMMERCIAL, AGERATING18, AGERATING12, STARTDATE, ENDDATE, UNKNOWN;

    companion object {
        fun parseValue(value: String): BlockReason {
            return try {
                valueOf(value)
            } catch (e: IllegalArgumentException) {
                UNKNOWN
            }
        }
    }
}

enum class Referrer {
    EDITOR, TRENDING, RECOMMENDATION
}

enum class VariantType {
    AUDIO_DESCRIPTION, SDH
}

enum class VariantSource {
    EXTERNAL, HLS, HDS, DASH
}

enum class SubtitleFormat {
    TTML, VTT
}

enum class Presentation {
    DEFAULT, VIDEO_360
}

enum class TokenType {
    AKAMAI, NONE
}

enum class StreamingMethod {
    PROGRESSIVE, M3UPLAYLIST, HLS, HDS, RTMP, DASH, UNKNOWN
}

enum class StreamType {
    LIVE,
    LIVE_WITH_DVR,
    ON_DEMAND
}

enum class AudioCodec {
    UNKNOWN,
    AAC,

    @Json(name = "AAC-HE")
    AAC_HE,
    MP3,
    MP2,
    WMAV2
}

enum class VideoCodec {
    H264, VP6F, MPEG2, WMV3, NONE, UNKNOWN
}

enum class MediaContainer {
    MP4, MKV, MPEG2_TS, FMP4, NONE, UNKNOWN
}

enum class MediaTypeFilter {
    @Json(name = "episode")
    EPISODE,

    @Json(name = "segment")
    SEGMENT,

    @Json(name = "clip")
    CLIP
}

enum class MediaFilter {
    EPISODE_ONLY, EPISODE_EXCLUDED
}

enum class TimeAvailability { NOT_YET_AVAILABLE, NOT_AVAILABLE_ANYMORE, AVAILABLE }

enum class SocialCountKey {
    @Json(name = "srgView")
    SRG_VIEW,

    @Json(name = "srgLike")
    SRG_LIKE,

    @Json(name = "fbShare")
    FB_SHARE,

    @Json(name = "googleShare")
    GOOGLE_SHARE,

    @Json(name = "twitterShare")
    TWITTER_SHARE,

    @Json(name = "whatsAppShare")
    WHATSAPP_SHARE
}

sealed interface SectionType2 {
    data class Unknow(val value: String)
}
