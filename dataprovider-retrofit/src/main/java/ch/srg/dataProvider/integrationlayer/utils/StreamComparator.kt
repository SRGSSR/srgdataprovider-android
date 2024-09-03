package ch.srg.dataProvider.integrationlayer.utils

import ch.srg.dataProvider.integrationlayer.data.remote.Quality
import ch.srg.dataProvider.integrationlayer.data.remote.Resource
import ch.srg.dataProvider.integrationlayer.data.remote.StreamingMethod

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class StreamComparator(
    private val orderedQualities: List<Quality>,
    private val orderedStreaming: List<StreamingMethod>,
    private val supportedDrms: List<Resource.Drm.Type>?,
    private val dvrSupported: Boolean,
) : Comparator<Resource> {
    override fun compare(lhs: Resource, rhs: Resource): Int {
        val l = score(lhs)
        val r = score(rhs)

        return l.compareTo(r)
    }

    fun score(resource: Resource): Int {
        if ((!dvrSupported && resource.dvr) || (!isSupportedDrm(resource.drmList))) {
            return SCORE_NOT_SUPPORTED
        }

        val indexOfStreaming = orderedStreaming.indexOf(resource.streamingMethod) // Will return -1 if not in the list
        val indexOfQuality = orderedQualities.indexOf(resource.quality) // Will return -1 if not in the list
        val scoreStreaming = (if (indexOfStreaming < 0) orderedStreaming.size else indexOfStreaming) * HANDICAP_STREAMING
        val scoreQuality = (if (indexOfQuality < 0) orderedQualities.size else indexOfQuality) * HANDICAP_QUALITY
        val scoreDvr = if (resource.dvr) 0 else HANDICAP_DVR

        return scoreStreaming + scoreQuality + scoreDvr
    }

    private fun isSupportedDrm(drmList: List<Resource.Drm>?): Boolean {
        if (drmList == null) {
            return true
        }

        if (supportedDrms.isNullOrEmpty()) {
            return false
        }

        return drmList.any { it.type in supportedDrms }
    }

    companion object {
        const val SCORE_NOT_SUPPORTED = Int.MAX_VALUE

        private const val HANDICAP_STREAMING = 1000
        private const val HANDICAP_DVR = 10
        private const val HANDICAP_QUALITY = 1
    }
}
