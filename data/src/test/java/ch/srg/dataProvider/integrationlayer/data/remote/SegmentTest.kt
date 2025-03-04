package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlinx.datetime.Clock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

class SegmentTest {
    @Test
    fun `resource reference date`() {
        val segment = Segment(
            id = "segment-id",
            mediaType = MediaType.VIDEO,
            vendor = Vendor.RTS,
            urn = "urn:rts:video:123456",
            title = "segment-title",
            markIn = 3.minutes.inWholeMilliseconds,
            markOut = 5.minutes.inWholeMilliseconds,
            type = Type.EPISODE,
            date = Clock.System.now(),
            duration = 90.minutes.inWholeMilliseconds,
            displayable = true,
            playableAbroad = true,
            imageUrl = ImageUrl("https://image.url/"),
        )

        assertNull(segment.markInDate)
        assertNull(segment.markOutDate)
        assertNull(segment.resourceReferenceDate)

        val currentDate = Clock.System.now()
        segment.resourceReferenceDate = currentDate
        assertEquals(currentDate + segment.markIn.milliseconds, segment.markInDate)
        assertEquals(currentDate + segment.markOut.milliseconds, segment.markOutDate)
        assertEquals(currentDate, segment.resourceReferenceDate)

        segment.resourceReferenceDate = null
        assertNull(segment.markInDate)
        assertNull(segment.markOutDate)
        assertNull(segment.resourceReferenceDate)
    }
}
