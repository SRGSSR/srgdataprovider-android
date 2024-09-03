package ch.srg.dataProvider.integrationlayer.utils

import ch.srg.dataProvider.integrationlayer.data.remote.Quality
import ch.srg.dataProvider.integrationlayer.data.remote.Resource
import ch.srg.dataProvider.integrationlayer.data.remote.StreamingMethod
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Collections

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
@RunWith(Parameterized::class)
class StreamComparatorAllPossibilitiesTest(
    private val qualitiesOrder: List<Quality>,
    private val streamingOrder: List<StreamingMethod>,
    private val dvrSupport: Boolean,
    private val drmSupported: Boolean,
    private val expectedResource: ExpectedResult,
) {

    class ExpectedResult(val quality: Quality, val streaming: StreamingMethod, val drmType: Resource.Drm.Type?, val dvr: Boolean) {
        constructor(quality: Quality, streaming: StreamingMethod, dvr: Boolean) : this(quality, streaming, null, dvr)
    }

    @Test
    fun firstDashHdDrmFirstDvr() {
        val comparator = StreamComparator(qualitiesOrder, streamingOrder, if (drmSupported) MediaFetcherInputs.DRM_TYPES else null, dvrSupport)
        val listResource = listAllPossibilities()
        sortAndPrint(comparator, listResource)
        val r = listResource[0]

        checkResource(r, expectedResource)
    }

    private fun checkResource(r: Resource, expectedResult: ExpectedResult) {
        Assert.assertNotNull(r.streamingMethod)
        Assert.assertNotNull(r.quality)
        Assert.assertEquals(expectedResult.streaming, r.streamingMethod)
        Assert.assertEquals(expectedResult.quality, r.quality)
        Assert.assertEquals("hasDrm", expectedResult.drmType != null, r.hasDrm())
        r.drmList?.let {
            Assert.assertTrue(it.contains(createDrm(Resource.Drm.Type.WIDEVINE)))
        }

        Assert.assertEquals("dvr", expectedResult.dvr, r.dvr)
    }

    private fun listAllPossibilities(): List<Resource> {
        val resourceList: MutableList<Resource> = ArrayList()
        // StreamComparator keep order in the resource list regarding to DRM situation.
        resourceList.add(
            ResourceBuilder.createResourceFrom(
                "hd_hls_drm_ios",
                Quality.HD,
                StreamingMethod.HLS,
                true,
                true,
                Resource.Drm.Type.FAIRPLAY
            )
        )

        resourceList.add(
            ResourceBuilder.createResourceFrom(
                "sd_dash_drm",
                Quality.SD,
                StreamingMethod.DASH,
                true,
                false,
                Resource.Drm.Type.PLAYREADY,
                Resource.Drm.Type.WIDEVINE
            )
        )
        resourceList.add(
            ResourceBuilder.createResourceFrom(
                "sd_dash_drm_dvr",
                Quality.SD,
                StreamingMethod.DASH,
                true,
                true,
                Resource.Drm.Type.PLAYREADY,
                Resource.Drm.Type.WIDEVINE
            )
        )
        resourceList.add(
            ResourceBuilder.createResourceFrom(
                "hd_dash_drm",
                Quality.HD,
                StreamingMethod.DASH,
                true,
                false,
                Resource.Drm.Type.PLAYREADY,
                Resource.Drm.Type.WIDEVINE
            )
        )
        resourceList.add(
            ResourceBuilder.createResourceFrom(
                "hd_dash_drm_dvr",
                Quality.HD,
                StreamingMethod.DASH,
                true,
                true,
                Resource.Drm.Type.PLAYREADY,
                Resource.Drm.Type.WIDEVINE
            )
        )
        resourceList.add(ResourceBuilder.createResourceFrom("sd_progressive", Quality.SD, StreamingMethod.PROGRESSIVE, true, false))
        resourceList.add(ResourceBuilder.createResourceFrom("sd_progressive_dvr", Quality.SD, StreamingMethod.PROGRESSIVE, true, true))
        resourceList.add(ResourceBuilder.createResourceFrom("hd_progressive", Quality.HD, StreamingMethod.PROGRESSIVE, true, false))
        resourceList.add(ResourceBuilder.createResourceFrom("hd_progressive_dvr", Quality.HD, StreamingMethod.PROGRESSIVE, true, true))

        resourceList.add(ResourceBuilder.createResourceFrom("sd_dash", Quality.SD, StreamingMethod.DASH, true, false))
        resourceList.add(ResourceBuilder.createResourceFrom("sd_dash_dvr", Quality.SD, StreamingMethod.DASH, true, true))
        resourceList.add(ResourceBuilder.createResourceFrom("hd_dash", Quality.HD, StreamingMethod.DASH, true, false))
        resourceList.add(ResourceBuilder.createResourceFrom("hd_dash_dvr", Quality.HD, StreamingMethod.DASH, true, true))

        resourceList.add(ResourceBuilder.createResourceFrom("sd_hls", Quality.SD, StreamingMethod.HLS, true, false))
        resourceList.add(ResourceBuilder.createResourceFrom("sd_hls_dvr", Quality.SD, StreamingMethod.HLS, true, true))
        resourceList.add(ResourceBuilder.createResourceFrom("hd_hls", Quality.HD, StreamingMethod.HLS, true, false))
        resourceList.add(ResourceBuilder.createResourceFrom("hd_hls_dvr", Quality.HD, StreamingMethod.HLS, true, true))

        return resourceList
    }


    private fun sortAndPrint(comparator: StreamComparator, resourceList: List<Resource>) {
        Collections.sort(resourceList, comparator)
        for (r in resourceList) {
            println(r.url + " -> " + comparator.score(r))
        }
    }

    companion object {
        var WITH_DVR: Boolean = true
        var NO_DVR: Boolean = false
        var WITH_DRM: Boolean = true
        var NO_DRM: Boolean = false

        var HLS_FIRST: List<StreamingMethod> = listOf(StreamingMethod.HLS, StreamingMethod.DASH, StreamingMethod.PROGRESSIVE)

        @JvmStatic
        @Parameterized.Parameters
        fun parameters(): Iterable<Array<Any>> {
            return listOf(
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, NO_DVR, WITH_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.DASH, Resource.Drm.Type.WIDEVINE, NO_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, WITH_DVR, WITH_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.DASH, Resource.Drm.Type.WIDEVINE, WITH_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, WITH_DVR, WITH_DRM,
                    ExpectedResult(Quality.SD, StreamingMethod.DASH, Resource.Drm.Type.WIDEVINE, WITH_DVR)
                ),

                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, NO_DVR, WITH_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.DASH, Resource.Drm.Type.WIDEVINE, NO_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, NO_DVR, WITH_DRM,
                    ExpectedResult(Quality.SD, StreamingMethod.DASH, Resource.Drm.Type.WIDEVINE, NO_DVR)
                ),

                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, NO_DVR, NO_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.DASH, NO_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, NO_DVR, NO_DRM,
                    ExpectedResult(Quality.SD, StreamingMethod.DASH, NO_DVR)
                ),

                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, WITH_DVR, NO_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.DASH, WITH_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, WITH_DVR, NO_DRM,
                    ExpectedResult(Quality.SD, StreamingMethod.DASH, WITH_DVR)
                ),

                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, NO_DVR, WITH_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.DASH, Resource.Drm.Type.WIDEVINE, NO_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, NO_DVR, WITH_DRM,
                    ExpectedResult(Quality.SD, StreamingMethod.DASH, Resource.Drm.Type.WIDEVINE, NO_DVR)
                ),

                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, HLS_FIRST, NO_DVR, NO_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.HLS, NO_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER, HLS_FIRST, NO_DVR, NO_DRM,
                    ExpectedResult(Quality.SD, StreamingMethod.HLS, NO_DVR)
                ),  // no drm type because no HLS resources with Widevine

                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER_HD, HLS_FIRST, NO_DVR, WITH_DRM,
                    ExpectedResult(Quality.HD, StreamingMethod.HLS, null, NO_DVR)
                ),
                arrayOf(
                    MediaFetcherInputs.QUALITY_ORDER, HLS_FIRST, NO_DVR, WITH_DRM,
                    ExpectedResult(Quality.SD, StreamingMethod.HLS, null, NO_DVR)
                ),
            )
        }

        private fun createDrm(type: Resource.Drm.Type): Resource.Drm {
            return Resource.Drm(type, "license", null)
        }

    }
}
