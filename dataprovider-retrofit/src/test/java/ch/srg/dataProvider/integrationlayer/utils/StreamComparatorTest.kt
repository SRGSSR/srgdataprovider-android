package ch.srg.dataProvider.integrationlayer.utils

import ch.srg.dataProvider.integrationlayer.data.remote.Quality
import ch.srg.dataProvider.integrationlayer.data.remote.Resource
import ch.srg.dataProvider.integrationlayer.data.remote.StreamingMethod
import org.junit.Assert
import org.junit.Test
import java.util.Arrays
import java.util.Collections

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
class StreamComparatorTest {
    @Test
    fun streamOrderWithUncompleteStreamingOrderList() {
        val listStreamingOrder: List<StreamingMethod> = listOf(StreamingMethod.DASH, StreamingMethod.HLS)
        val comparator = StreamComparator(MediaFetcherInputs.QUALITY_ORDER, listStreamingOrder, null, true)

        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.PROGRESSIVE, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        sortAndPrint(comparator, resourceList)
        Assert.assertEquals(resourceList[0].streamingMethod, StreamingMethod.DASH)

        val resourceList1: MutableList<Resource> = ArrayList()
        resourceList1.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList1.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.PROGRESSIVE, false, false))
        resourceList1.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        sortAndPrint(comparator, resourceList1)
        Assert.assertEquals(resourceList1[0].streamingMethod, StreamingMethod.DASH)

        val resourceList2: MutableList<Resource> = ArrayList()
        resourceList2.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        resourceList2.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.PROGRESSIVE, false, false))
        resourceList2.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        sortAndPrint(comparator, resourceList2)
        Assert.assertEquals(resourceList2[0].streamingMethod, StreamingMethod.DASH)
    }

    @Test
    fun streamOrderWithUncompleteQualityOrderList() {
        val listQualityOrder = Arrays.asList(Quality.HD, Quality.SD)
        val comparator = StreamComparator(listQualityOrder, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, null, true)

        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("1", Quality.HQ, StreamingMethod.DASH, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("2", Quality.SD, StreamingMethod.DASH, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        sortAndPrint(comparator, resourceList)
        Assert.assertEquals(resourceList[0].quality, Quality.HD)

        val resourceList1: MutableList<Resource> = ArrayList()
        resourceList1.add(ResourceBuilder.createResourceFrom("2", Quality.SD, StreamingMethod.DASH, false, false))
        resourceList1.add(ResourceBuilder.createResourceFrom("1", Quality.HQ, StreamingMethod.DASH, false, false))
        resourceList1.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        sortAndPrint(comparator, resourceList1)
        Assert.assertEquals(resourceList1[0].quality, Quality.HD)

        val resourceList2: MutableList<Resource> = ArrayList()
        resourceList2.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        resourceList2.add(ResourceBuilder.createResourceFrom("1", Quality.HQ, StreamingMethod.DASH, false, false))
        resourceList2.add(ResourceBuilder.createResourceFrom("2", Quality.SD, StreamingMethod.DASH, false, false))
        sortAndPrint(comparator, resourceList2)
        Assert.assertEquals(resourceList2[0].quality, Quality.HD)
        Assert.assertEquals(resourceList2[1].quality, Quality.SD)
    }

    @Test
    fun dashFirst() {
        val comparator = StreamComparator(MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, null, false)

        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.PROGRESSIVE, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("4", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))

        sortAndPrint(comparator, resourceList)
        Assert.assertEquals(resourceList[0].streamingMethod, StreamingMethod.DASH)
    }

    @Test
    fun hlsFirstWhenNoDash() {
        val comparator = StreamComparator(
            MediaFetcherInputs.QUALITY_ORDER_HD, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, MediaFetcherInputs
                .DRM_TYPES, false
        )

        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("3", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.PROGRESSIVE, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))

        sortAndPrint(comparator, resourceList)
        Assert.assertEquals(resourceList[0].streamingMethod, StreamingMethod.HLS)
    }

    @Test
    fun unsupportedDvr() {
        val comparator = StreamComparator(MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, null, false)

        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.DASH, false, true))
        resourceList.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("4", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        sortAndPrint(comparator, resourceList)
        Assert.assertEquals("3", resourceList[0].url)

        val resourceList2: MutableList<Resource> = ArrayList()
        resourceList2.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.DASH, false, true))
        resourceList2.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList2.add(ResourceBuilder.createResourceFrom("4", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        sortAndPrint(comparator, resourceList2)
        Assert.assertEquals("2", resourceList2[0].url)
    }

    @Test
    fun unsupportedDvrWithDrmFirst() {
        val comparator = StreamComparator(MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, null, false)

        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.DASH, false, true))
        resourceList.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("4", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        sortAndPrint(comparator, resourceList)
        Assert.assertEquals("3", resourceList[0].url)

        val resourceList2: MutableList<Resource> = ArrayList()
        resourceList2.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.DASH, false, true))
        resourceList2.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList2.add(ResourceBuilder.createResourceFrom("4", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        sortAndPrint(comparator, resourceList2)
        Assert.assertEquals("2", resourceList2[0].url)
    }

    @Test
    fun unsupportedDrm() {
        val comparator = StreamComparator(MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, null, false)

        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.DASH, false, false, Resource.Drm.Type.FAIRPLAY))
        resourceList.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("3", Quality.HD, StreamingMethod.DASH, false, false))
        resourceList.add(ResourceBuilder.createResourceFrom("4", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        sortAndPrint(comparator, resourceList)
        Assert.assertEquals("3", resourceList[0].url)

        val resourceList2: MutableList<Resource> = ArrayList()
        resourceList2.add(ResourceBuilder.createResourceFrom("1", Quality.HD, StreamingMethod.DASH, false, false, Resource.Drm.Type.FAIRPLAY))
        resourceList2.add(ResourceBuilder.createResourceFrom("2", Quality.HD, StreamingMethod.HLS, false, false))
        resourceList2.add(ResourceBuilder.createResourceFrom("4", Quality.SD, StreamingMethod.PROGRESSIVE, false, false))
        sortAndPrint(comparator, resourceList2)
        Assert.assertEquals("2", resourceList2[0].url)
    }

    @Test
    fun nullStreamingType() {
        val comparator = StreamComparator(MediaFetcherInputs.QUALITY_ORDER, MediaFetcherInputs.STREAMING_ORDER_DEFAULT, null, true)

        val resourceList = listWithNullStreamingType()
        sortAndPrint(comparator, resourceList)

        Assert.assertNotEquals("null", resourceList[0].url)
    }

    private fun listWithNullStreamingType(): List<Resource> {
        val resourceList: MutableList<Resource> = ArrayList()
        resourceList.add(ResourceBuilder.createResourceFrom("hsl", Quality.HD, StreamingMethod.HLS, false, true))
        resourceList.add(ResourceBuilder.createResourceFrom("hsl", Quality.HD, StreamingMethod.DASH, false, true))
        resourceList.add(ResourceBuilder.createResourceFrom("drm_fairplay", Quality.HD, StreamingMethod.HLS, false, true, Resource.Drm.Type.FAIRPLAY))
        resourceList.add(
            ResourceBuilder.createResourceFrom(
                "drm_playready",
                Quality.HD,
                StreamingMethod.DASH,
                false,
                true,
                Resource.Drm.Type.PLAYREADY
            )
        )
        resourceList.add(
            ResourceBuilder.createResourceFrom(
                "drm_widevine",
                Quality.HD,
                StreamingMethod.DASH,
                false,
                true,
                Resource.Drm.Type.WIDEVINE
            )
        )
        return resourceList
    }

    private fun sortAndPrint(comparator: StreamComparator, resourceList: List<Resource>) {
        Collections.sort(resourceList, comparator)
        for (r in resourceList) {
            println(r.url + " -> " + comparator.score(r))
        }
    }

}
