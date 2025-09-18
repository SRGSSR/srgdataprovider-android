package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

class MediaCompositionTest {
    @Test(expected = IllegalStateException::class)
    fun `get main chapter with empty chapter list`() {
        val mediaComposition = MediaComposition(
            chapterUrn = "urn:rts:video:123456",
            chapterList = emptyList(),
        )

        mediaComposition.getMainChapter()
    }

    @Test(expected = IllegalStateException::class)
    fun `get main chapter with chapter list without matching chapter`() {
        val mediaComposition = MediaComposition(
            chapterUrn = "urn:rts:video:123456",
            chapterList = listOf(
                createChapter(urn = "urn:rts:video:456789"),
            ),
        )

        mediaComposition.getMainChapter()
    }

    @Test
    fun `get main chapter with chapter list with matching chapter`() {
        val mainChapter = createChapter(urn = "urn:rts:video:123456")
        val mediaComposition = MediaComposition(
            chapterUrn = mainChapter.urn,
            chapterList = listOf(createChapter(urn = "urn:rts:video:456789"), mainChapter),
        )

        assertEquals(mainChapter, mediaComposition.getMainChapter())
    }

    private companion object {
        private fun createChapter(
            urn: String,
            segmentList: List<Segment>? = null,
        ): Chapter {
            return Chapter(
                id = "chapter-id",
                mediaType = MediaType.VIDEO,
                vendor = Vendor.RTS,
                urn = urn,
                title = "chapter-title",
                imageUrl = ImageUrl("https://image.url"),
                type = Type.EPISODE,
                date = Clock.System.now(),
                duration = 90.minutes.inWholeMilliseconds,
                segmentList = segmentList,
            )
        }
    }
}
