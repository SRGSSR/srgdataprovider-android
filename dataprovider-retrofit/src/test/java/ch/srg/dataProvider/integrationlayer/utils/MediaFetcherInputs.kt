package ch.srg.dataProvider.integrationlayer.utils

import ch.srg.dataProvider.integrationlayer.data.remote.Quality
import ch.srg.dataProvider.integrationlayer.data.remote.Resource
import ch.srg.dataProvider.integrationlayer.data.remote.StreamingMethod

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
object MediaFetcherInputs {
    @JvmField
    @Transient
    val STREAMING_ORDER_DEFAULT: List<StreamingMethod> = listOf(
        StreamingMethod.DASH,
        StreamingMethod.HLS,
        StreamingMethod.PROGRESSIVE
    )

    @Transient
    val DRM_TYPES: List<Resource.Drm.Type> = listOf(
        Resource.Drm.Type.WIDEVINE, Resource.Drm.Type.PLAYREADY
    )

    @JvmField
    @Transient
    val QUALITY_ORDER: List<Quality> = listOf(
        Quality.SD,
        Quality.HQ,
        Quality.HD
    )

    @JvmField
    @Transient
    val QUALITY_ORDER_HD: List<Quality> = listOf(
        Quality.HD,
        Quality.HQ,
        Quality.SD
    )
}
