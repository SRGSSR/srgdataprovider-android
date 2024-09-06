package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlin.test.Test
import kotlin.test.assertEquals

class ProgramGuideTest {
    @Test
    fun `find channel list with null program list`() {
        val programGuide = ProgramGuide(data = null)

        assertEquals(emptyList(), programGuide.findChannelList())
    }

    @Test
    fun `find channel list with empty program list`() {
        val programGuide = ProgramGuide(data = emptyList())

        assertEquals(emptyList(), programGuide.findChannelList())
    }

    @Test
    fun `find channel list with program list`() {
        val channels = buildList(5) {
            repeat(5) { index ->
                add(
                    Channel(
                        id = "channel-id-$index",
                        vendor = Vendor.RTS,
                        urn = "urn:rts:video:$index",
                        title = "channel-title$index",
                        imageUrl = ImageUrl("https://image.url/"),
                        transmission = Transmission.TV,
                    )
                )
            }
        }
        val programGuide = ProgramGuide(
            data = channels.map {
                ProgramCompositionListResult(
                    channel = it,
                    data = null,
                )
            },
        )

        assertEquals(channels, programGuide.findChannelList())
    }
}
