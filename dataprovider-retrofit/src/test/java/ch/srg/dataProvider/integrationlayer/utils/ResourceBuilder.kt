package ch.srg.dataProvider.integrationlayer.utils

import ch.srg.dataProvider.integrationlayer.data.remote.Quality
import ch.srg.dataProvider.integrationlayer.data.remote.Resource
import ch.srg.dataProvider.integrationlayer.data.remote.Resource.Drm
import ch.srg.dataProvider.integrationlayer.data.remote.StreamingMethod

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
object ResourceBuilder {
    fun createResourceFrom(
        url: String,
        quality: Quality,
        streaming: StreamingMethod,
        live: Boolean,
        dvr: Boolean,
        vararg drmTypes: Drm.Type
    ): Resource {
        var drmList: MutableList<Drm>? = null
        if (drmTypes.isNotEmpty()) {
            drmList = ArrayList()
            for (type in drmTypes) {
                drmList.add(Drm(type, "license", null))
            }
        }
        return Resource(url = url, quality = quality, streamingMethod = streaming, live = live, dvr = dvr, drmList = drmList)
    }
}
