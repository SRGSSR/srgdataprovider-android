package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

class ChapterTest {
    @Test
    fun `find segment with null segment list`() {
        val chapter = createChapter(segmentList = null)

        assertNull(chapter.findSegment("urn:rts:video:123456"))
    }

    @Test
    fun `find segment with empty segment list`() {
        val chapter = createChapter(segmentList = emptyList())

        assertNull(chapter.findSegment("urn:rts:video:123456"))
    }

    @Test
    fun `find segment with segment list`() {
        val segment = createSegment(SEGMENT_URN)
        val chapter = createChapter(segmentList = listOf(createSegment("urn:rts:video:987654"), segment))

        assertNull(chapter.findSegment("urn:rts:video:123456"))
        assertEquals(segment, chapter.findSegment(SEGMENT_URN))
    }

    @Test
    fun `find 360 resource with null resource list`() {
        val chapter = createChapter(resourceList = null)

        assertNull(chapter.find360Resource())
        assertFalse(chapter.is360())
    }

    @Test
    fun `find 360 resource with empty resource list`() {
        val chapter = createChapter(resourceList = emptyList())

        assertNull(chapter.find360Resource())
        assertFalse(chapter.is360())
    }

    @Test
    fun `find 360 resource without 360 resource in resource list`() {
        val chapter = createChapter(resourceList = listOf(createResource(), createResource()))

        assertNull(chapter.find360Resource())
        assertFalse(chapter.is360())
    }

    @Test
    fun `find 360 resource with 360 resource in resource list`() {
        val resource = createResource(presentation = Presentation.VIDEO_360)
        val chapter = createChapter(resourceList = listOf(createResource(), resource))

        assertEquals(resource, chapter.find360Resource())
        assertTrue(chapter.is360())
    }

    @Test
    fun `does have blocked segment with null segment list`() {
        val chapter = createChapter(segmentList = null)

        assertFalse(chapter.doesHaveBlockedSegment())
    }

    @Test
    fun `does have blocked segment with empty segment list`() {
        val chapter = createChapter(segmentList = emptyList())

        assertFalse(chapter.doesHaveBlockedSegment())
    }

    @Test
    fun `does have blocked segment with segment list`() {
        val segment = createSegment(SEGMENT_URN, blocked = true)
        val chapter = createChapter(segmentList = listOf(createSegment("urn:rts:video:987654"), segment))

        assertTrue(chapter.doesHaveBlockedSegment())
    }

    private companion object {
        private const val SEGMENT_URN = "urn:rts:video:456789"

        private fun createChapter(
            segmentList: List<Segment>? = null,
            resourceList: List<Resource>? = null,
        ): Chapter {
            return Chapter(
                id = "chapter-id",
                mediaType = MediaType.VIDEO,
                vendor = Vendor.RTS,
                urn = "urn:rts:video:123456",
                title = "chapter-title",
                imageUrl = ImageUrl("https://image.url"),
                type = Type.EPISODE,
                date = Clock.System.now(),
                duration = 90.minutes.inWholeMilliseconds,
                segmentList = segmentList,
                resourceList = resourceList,
            )
        }

        private fun createSegment(
            urn: String,
            blocked: Boolean = false,
        ): Segment {
            return Segment(
                id = "segment-id",
                mediaType = MediaType.VIDEO,
                vendor = Vendor.RTS,
                urn = urn,
                title = "segment-title",
                markIn = 0L,
                markOut = 0L,
                type = Type.CLIP,
                date = Clock.System.now(),
                duration = 30.minutes.inWholeMilliseconds,
                displayable = true,
                playableAbroad = true,
                imageUrl = ImageUrl("https://image.url"),
                blockReason = BlockReason.LEGAL.takeIf { blocked },
            )
        }

        private fun createResource(presentation: Presentation = Presentation.DEFAULT): Resource {
            return Resource(
                url = "https://resource.url",
                quality = Quality.HD,
                presentation = presentation,
                streamingMethod = StreamingMethod.DASH,
            )
        }
    }
}
