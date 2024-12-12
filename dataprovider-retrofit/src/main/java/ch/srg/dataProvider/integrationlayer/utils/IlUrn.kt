package ch.srg.dataProvider.integrationlayer.utils

import java.util.regex.Pattern

/**
 * This very simple class represents a URN provided by the IL.
 */
class IlUrn private constructor() {
    private lateinit var underlyingUrn: String

    /**
     * Returns Business Unit.
     */
    lateinit var bu: String
        private set

    /**
     * Return Asset Type (either audio or video)
     */
    lateinit var assetType: String
        private set

    /**
     * Return ID
     */
    lateinit var id: String
        private set

    val isAudio: Boolean
        get() = assetType == ASSET_AUDIO

    val isVideo: Boolean
        get() = assetType == ASSET_VIDEO || assetType == ASSET_VIDEO_SET

    val isShow: Boolean
        get() = assetType == ASSET_SHOW

    /**
     * @param urn urn can be any media identifier, valid or not
     * @throws IllegalArgumentException if not a valid urn
     */
    @Throws(IllegalArgumentException::class)
    constructor(urn: String) : this() {
        require(parseBuMam(urn) || parseSwissTxt(urn)) {
            "URN '$urn' does not match a valid URN pattern."
        }
    }

    constructor(bu: String, assetType: String, id: String) : this() {
        this.bu = bu
        this.assetType = assetType
        this.id = id

        underlyingUrn = "urn:" + this.bu + ":" + this.assetType + ":" + this.id
    }

    fun equalsToString(other: String): Boolean {
        return try {
            underlyingUrn == IlUrn(other).toString()
        } catch (_: IllegalArgumentException) {
            false
        }
    }

    override fun toString(): String {
        return underlyingUrn
    }

    @Suppress("MagicNumber")
    private fun parseBuMam(urn: String): Boolean {
        val matcher = PATTERN_BUMAM.matcher(urn)
        if (matcher.matches()) {
            bu = checkNotNull(matcher.group(1)).lowercase()
            assetType = checkNotNull(matcher.group(2)).lowercase()
            id = checkNotNull(matcher.group(3)) // Do not transform ID since it is case-sensitive.

            if (assetType == ASSET_GROUP) {
                // Is a synonym of show (used in search request URN)
                assetType = ASSET_SHOW
            }

            underlyingUrn = "urn:$bu:$assetType:$id"

            return true
        } else {
            return false
        }
    }

    @Suppress("MagicNumber")
    private fun parseSwissTxt(urn: String): Boolean {
        val matcher = PATTERN_SWISSTXT.matcher(urn)
        if (matcher.matches()) {
            val swissTxt = matcher.group(1)?.lowercase()
            bu = checkNotNull(matcher.group(3)).lowercase()
            assetType = checkNotNull(matcher.group(2)).lowercase()
            id = checkNotNull(matcher.group(4))

            if (assetType == ASSET_GROUP) {
                // Is a synonym of show (used in search request URN)
                assetType = ASSET_SHOW
            }

            underlyingUrn = "urn:$swissTxt:$assetType:$bu:$id"

            return true
        } else {
            return false
        }
    }

    companion object {
        const val ASSET_VIDEO = "video"
        const val ASSET_VIDEO_SET = "videoset"
        const val ASSET_AUDIO = "audio"
        const val ASSET_SHOW = "show"
        const val ASSET_GROUP = "assetgroup"

        private val PATTERN_BUMAM =
            "^urn:(srf|rts|rsi|rtr|swi|default):(?:[^:]+:)?(video|audio|videoset|show|assetgroup):([^:]+)$".toPattern(Pattern.CASE_INSENSITIVE)
        private val PATTERN_SWISSTXT =
            "^urn:(swisstxt):(?:[^:]+:)?(video|audio|videoset|show|assetgroup):(srf|rts|rsi|rtr|swi):([^:]+)$".toPattern(Pattern.CASE_INSENSITIVE)

        /**
         * @param string potential URN
         * @return true iff a valid urn (false if string is null)
         */
        @JvmStatic
        fun isUrn(string: String?): Boolean {
            return string != null && (PATTERN_BUMAM.matcher(string).matches() || PATTERN_SWISSTXT.matcher(string).matches())
        }

        @JvmStatic
        fun format(bu: String, assetType: String, id: String): String {
            return "urn:$bu:$assetType:$id"
        }

        /**
         * @param urn urn can be any media identifier, valid or not
         * @return true if urn is valid and of video type
         */
        @JvmStatic
        fun isAudio(urn: String?): Boolean {
            return urn != null && isUrn(urn) && IlUrn(urn).isAudio
        }

        /**
         * @param urn urn can be any media identifier, valid or not
         * @return true if urn is valid and of video type
         */
        @JvmStatic
        fun isVideo(urn: String?): Boolean {
            return urn != null && isUrn(urn) && IlUrn(urn).isVideo
        }

        /**
         * Get Id for given urn string.
         *
         * @param urn urn can be any media identifier, valid or not
         * @return id or full string if unparseable URN
         */
        @JvmStatic
        fun getId(urn: String?): String? {
            return if (urn != null && isUrn(urn)) IlUrn(urn).id else urn
        }

        /**
         * Get Asset Type for given urn string.
         *
         * @param urn urn can be any media identifier, valid or not
         * @return assetType or "tv" if unparseable URN
         */
        @JvmStatic
        fun getAssetType(urn: String?): String {
            return if (urn != null && isUrn(urn)) IlUrn(urn).assetType else ASSET_VIDEO
        }
    }
}
