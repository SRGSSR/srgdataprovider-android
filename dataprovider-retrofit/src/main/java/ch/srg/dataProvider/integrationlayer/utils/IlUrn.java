package ch.srg.dataProvider.integrationlayer.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This very simple class represents a URN provided by the IL.
 */
public class IlUrn {
    private final static Pattern PATTERN_BUMAM = Pattern.compile("^urn:(srf|rts|rsi|rtr|swi|default):(?:[^:]+:)?(video|audio|videoset|show|assetgroup):([^:]+)$", Pattern.CASE_INSENSITIVE);
    private final static Pattern PATTERN_SWISSTXT = Pattern.compile("^urn:(swisstxt):(?:[^:]+:)?(video|audio|videoset|show|assetgroup):(srf|rts|rsi|rtr|swi):([^:]+)$", Pattern.CASE_INSENSITIVE);

    public static final String ASSET_VIDEO = "video";
    public static final String ASSET_VIDEO_SET = "videoset";
    public static final String ASSET_AUDIO = "audio";
    public static final String ASSET_SHOW = "show";
    public static final String ASSET_GROUP = "assetgroup";

    private String underlying;

    private String bu;
    private String assetType;
    private String id;

    /**
     * @param urn urn can be any media identifier, valid or not
     * @throws IllegalArgumentException if not a valid urn
     */
    public IlUrn(@NonNull String urn) throws IllegalArgumentException {
        if (!parseBuMam(urn)
            && !parseSwissTxt(urn)) {
            throw new IllegalArgumentException(String.format("URN '%s' does not match a valid URN pattern.", urn));
        }
    }

    private boolean parseBuMam(@NonNull String urn) {
        Matcher matcher;
        matcher = PATTERN_BUMAM.matcher(urn);
        if (matcher.matches()) {
            bu = matcher.group(1).toLowerCase(Locale.US);
            assetType = matcher.group(2).toLowerCase(Locale.US);

            if (assetType.equals(ASSET_GROUP)) {
                // is a synonym of show (used in search request URN)
                assetType = ASSET_SHOW;
            }

            id = matcher.group(3); // Do not transform ID since it is case-sensitive.
            underlying = "urn:" + bu + ":" + assetType + ":" + id;
            return true;
        } else {
            return false;
        }
    }

    private boolean parseSwissTxt(@NonNull String urn) {
        Matcher matcher = PATTERN_SWISSTXT.matcher(urn);
        if (matcher.matches()) {
            String swisstxt = matcher.group(1).toLowerCase(Locale.US);
            bu = matcher.group(3).toLowerCase(Locale.US);
            assetType = matcher.group(2).toLowerCase(Locale.US);
            if (assetType.equals(ASSET_GROUP)) {
                // is a synonym of show (used in search request URN)
                assetType = ASSET_SHOW;
            }
            id = matcher.group(4);
            underlying = "urn:" + swisstxt + ":" + assetType + ":" + bu + ":" + id;
            return true;
        } else {
            return false;
        }
    }

    public IlUrn(@Nullable String bu, @Nullable String assetType, @Nullable String id) {
        this.bu = bu == null ? "default" : bu;
        this.assetType = assetType;
        this.id = id;
        underlying = "urn:" + this.bu + ":" + this.assetType + ":" + this.id;
    }

    /**
     * @param string potential URN
     * @return true iff a valid urn (false if string is null)
     */
    public static boolean isUrn(@Nullable String string) {
        return string != null && (PATTERN_BUMAM.matcher(string).matches() || PATTERN_SWISSTXT.matcher(string).matches());
    }


    public static String format(String bu, String assetType, String id) {
        return String.format("urn:%s:%s:%s", bu, assetType, id);
    }

    /**
     * Returns Business Unit.
     */
    public String getBu() {
        return bu;
    }

    /**
     * Return Asset Type (either audio or video)
     */
    public String getAssetType() {
        return assetType;
    }

    /**
     * Return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the underlying string representation.
     */
    @NonNull
    @Override
    public String toString() {
        return underlying;
    }

    public boolean isAudio() {
        return TextUtils.equals(ASSET_AUDIO, assetType);
    }

    public boolean isVideo() {
        return TextUtils.equals(ASSET_VIDEO, assetType) || TextUtils.equals(ASSET_VIDEO_SET, assetType);
    }

    /**
     * @param urn urn can be any media identifier, valid or not
     * @return true if urn is valid and of video type
     */
    public static boolean isAudio(@Nullable String urn) {
        return isUrn(urn) && new IlUrn(urn).isAudio();
    }

    /**
     * @param urn urn can be any media identifier, valid or not
     * @return true if urn is valid and of video type
     */
    public static boolean isVideo(@Nullable String urn) {
        return isUrn(urn) && new IlUrn(urn).isVideo();
    }

    public boolean isShow() {
        return TextUtils.equals(ASSET_SHOW, assetType);
    }


    public boolean equalsToString(@NonNull String o) {
        try {
            return underlying.equals(new IlUrn(o).toString());
        } catch (IllegalArgumentException invalidUrnException) {
            return false;
        }
    }

    /**
     * Get Id for given urn string.
     *
     * @param urn urn can be any media identifier, valid or not
     * @return id or full string if unparseable URN
     */
    public static String getId(@Nullable String urn) {
        return isUrn(urn) ? new IlUrn(urn).getId() : urn;
    }

    /**
     * Get Asset Type for given urn string.
     *
     * @param urn urn can be any media identifier, valid or not
     * @return assetType or "tv" if unparseable URN
     */
    public static String getAssetType(@Nullable String urn) {
        return isUrn(urn) ? new IlUrn(urn).getAssetType() : ASSET_VIDEO;
    }
}
