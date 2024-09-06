package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import java.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
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
            date = Date(),
            duration = 90.minutes.inWholeMilliseconds,
            displayable = true,
            playableAbroad = true,
            imageUrl = ImageUrl("https://image.url/"),
        )

        assertNull(segment.markInDate)
        assertNull(segment.markOutDate)
        assertNull(segment.resourceReferenceDate)

        val currentDate = Date()
        segment.resourceReferenceDate = currentDate
        assertEquals(Date(currentDate.time + segment.markIn), segment.markInDate)
        assertEquals(Date(currentDate.time + segment.markOut), segment.markOutDate)
        assertEquals(currentDate, segment.resourceReferenceDate)

        segment.resourceReferenceDate = null
        assertNull(segment.markInDate)
        assertNull(segment.markOutDate)
        assertNull(segment.resourceReferenceDate)
    }
}
