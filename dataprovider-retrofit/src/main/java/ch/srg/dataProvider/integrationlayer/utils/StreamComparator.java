package ch.srg.dataProvider.integrationlayer.utils;

import androidx.annotation.Nullable;

import java.util.Comparator;
import java.util.List;

import ch.srg.dataProvider.integrationlayer.data.Quality;
import ch.srg.dataProvider.integrationlayer.data.Resource;
import ch.srg.dataProvider.integrationlayer.data.StreamingMethod;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class StreamComparator implements Comparator<Resource> {
    private static final int HANDICAP_STREAMING = 1000;
    private static final int HANDICAP_DVR = 10;
    private static final int HANDICAP_QUALITY = 1;

    public static final int SCORE_NOT_SUPPORTED = Integer.MAX_VALUE;

    private List<Quality> orderedQualities;
    private List<StreamingMethod> orderedStreaming;
    @Nullable
    private List<Resource.Drm.Type> supportedDrms;
    private boolean dvrSupported;

    public StreamComparator(List<Quality> orderedQualities, List<StreamingMethod> orderedStreaming, @Nullable List<Resource.Drm.Type> supportedDrms, boolean dvrSupported) {
        this.orderedQualities = orderedQualities;
        this.orderedStreaming = orderedStreaming;
        this.supportedDrms = supportedDrms;
        this.dvrSupported = dvrSupported;
    }

    @Override
    public int compare(Resource lhs, Resource rhs) {
        int l = score(lhs);
        int r = score(rhs);
        //noinspection UseCompareMethod
        return l == r ? 0 : ((l > r) ? 1 : -1);
    }

    public int score(Resource r) {
        if (r.getStreamingMethod() == null
                || (!dvrSupported && r.getDvr())
                || (!isSupportedDrm(r.getDrmList()))) {
            return SCORE_NOT_SUPPORTED;
        }
        int indexOfStreaming = orderedStreaming.indexOf(r.getStreamingMethod()); // will retrun -1 if not in the list
        int indexOfQuality = orderedQualities.indexOf(r.getQuality()); // will retrun -1 if not in the list

        return (indexOfStreaming < 0 ? orderedStreaming.size() : indexOfStreaming) * HANDICAP_STREAMING
                + (indexOfQuality < 0 ? orderedQualities.size() : indexOfQuality) * HANDICAP_QUALITY
                + (r.getDvr() ? 0 : HANDICAP_DVR);
    }

    private boolean isSupportedDrm(List<Resource.Drm> drmList) {
        if (drmList == null) {
            return true;
        }
        if (supportedDrms != null) {
            for (Resource.Drm drm : drmList) {
                if (supportedDrms.contains(drm.getType())) {
                    return true;
                }
            }
        }
        return false;
    }
}
