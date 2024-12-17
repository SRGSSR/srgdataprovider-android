package ch.srg.dataProvider.integrationlayer.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class SpriteSheetTest {
    @Test
    fun `sprite sheet`() {
        val spriteSheet = SpriteSheet(
            urn = "urn:rts:video:123456",
            rows = 3,
            columns = 5,
            thumbnailHeight = 600,
            thumbnailWidth = 800,
            interval = 5.minutes.inWholeMilliseconds,
            url = "https://url.com/video.mp4",
        )

        assertEquals(15, spriteSheet.count)
        // TODO Enable this once #50 is merged
        // assertEquals(AspectRatio(800, 600), spriteSheet.aspectRatio)
    }
}
