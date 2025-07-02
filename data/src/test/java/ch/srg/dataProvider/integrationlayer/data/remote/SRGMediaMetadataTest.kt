package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.data.remote.BlockReason.ENDDATE
import ch.srg.dataProvider.integrationlayer.data.remote.BlockReason.STARTDATE
import ch.srg.dataProvider.integrationlayer.data.remote.TimeAvailability.AVAILABLE
import ch.srg.dataProvider.integrationlayer.data.remote.TimeAvailability.NOT_AVAILABLE_ANYMORE
import ch.srg.dataProvider.integrationlayer.data.remote.TimeAvailability.NOT_YET_AVAILABLE
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import java.util.Calendar.AUGUST
import java.util.Calendar.JULY
import java.util.Calendar.SEPTEMBER
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

class SRGMediaMetadataTest {
    @Test
    fun `get download uri`() {
        val metadata = createMediaMetadata()

        assertNull(metadata.copy(podcastSdUrl = null, podcastHdUrl = null).getDownloadUri())
        assertEquals(HD_URL, metadata.copy(podcastSdUrl = null, podcastHdUrl = HD_URL).getDownloadUri())
        assertEquals(SD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = null).getDownloadUri())
        assertEquals(HD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = HD_URL).getDownloadUri())

        assertNull(metadata.copy(podcastSdUrl = null, podcastHdUrl = null).getDownloadUri(Quality.SD))
        assertNull(metadata.copy(podcastSdUrl = null, podcastHdUrl = HD_URL).getDownloadUri(Quality.SD))
        assertEquals(SD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = null).getDownloadUri(Quality.SD))
        assertEquals(SD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = HD_URL).getDownloadUri(Quality.SD))

        assertNull(metadata.copy(podcastSdUrl = null, podcastHdUrl = null).getDownloadUri(Quality.HD))
        assertEquals(HD_URL, metadata.copy(podcastSdUrl = null, podcastHdUrl = HD_URL).getDownloadUri(Quality.HD))
        assertEquals(SD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = null).getDownloadUri(Quality.HD))
        assertEquals(HD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = HD_URL).getDownloadUri(Quality.HD))

        assertNull(metadata.copy(podcastSdUrl = null, podcastHdUrl = null).getDownloadUri(Quality.HQ))
        assertEquals(HD_URL, metadata.copy(podcastSdUrl = null, podcastHdUrl = HD_URL).getDownloadUri(Quality.HQ))
        assertEquals(SD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = null).getDownloadUri(Quality.HQ))
        assertEquals(HD_URL, metadata.copy(podcastSdUrl = SD_URL, podcastHdUrl = HD_URL).getDownloadUri(Quality.HQ))
    }

    @Test
    fun `is blocked`() {
        val metadata = createMediaMetadata()
        val atBeforeFrom = createInstant(2024, JULY, 15)
        val atBetweenFromTo = createInstant(2024, AUGUST, 15)
        val atAfterTo = createInstant(2024, SEPTEMBER, 15)
        val validFrom = createInstant(2024, AUGUST, 1)
        val validTo = createInstant(2024, AUGUST, 31)

        assertFalse(metadata.copy(blockReason = null, validFrom = null, validTo = null).isBlocked())
        assertFalse(metadata.copy(blockReason = null, validFrom = null, validTo = null).isBlocked(atBeforeFrom))
        assertFalse(metadata.copy(blockReason = null, validFrom = validFrom, validTo = null).isBlocked())
        assertTrue(metadata.copy(blockReason = null, validFrom = validFrom, validTo = null).isBlocked(atBeforeFrom))
        assertFalse(metadata.copy(blockReason = null, validFrom = validFrom, validTo = null).isBlocked(atAfterTo))
        assertTrue(metadata.copy(blockReason = null, validFrom = null, validTo = validTo).isBlocked())
        assertFalse(metadata.copy(blockReason = null, validFrom = null, validTo = validTo).isBlocked(atBeforeFrom))
        assertTrue(metadata.copy(blockReason = null, validFrom = null, validTo = validTo).isBlocked(atAfterTo))
        assertTrue(metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).isBlocked())
        assertTrue(metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).isBlocked(atBeforeFrom))
        assertFalse(metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).isBlocked(atBetweenFromTo))
        assertTrue(metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).isBlocked(atAfterTo))

        BlockReason.entries.forEach { blockReason ->
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = null, validTo = null).isBlocked())
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = null, validTo = null).isBlocked(atBeforeFrom))
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = null).isBlocked())
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = null).isBlocked(atBeforeFrom))
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = null).isBlocked(atAfterTo))
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = null, validTo = validTo).isBlocked())
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = null, validTo = validTo).isBlocked(atBeforeFrom))
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = null, validTo = validTo).isBlocked(atAfterTo))
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).isBlocked())
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).isBlocked(atBeforeFrom))
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).isBlocked(atBetweenFromTo))
            assertTrue(metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).isBlocked(atAfterTo))
        }
    }

    @Test
    fun `get time availability`() {
        val metadata = createMediaMetadata()
        val atBeforeFrom = createInstant(2024, JULY, 15)
        val atBetweenFromTo = createInstant(2024, AUGUST, 15)
        val atAfterTo = createInstant(2024, SEPTEMBER, 15)
        val validFrom = createInstant(2024, AUGUST, 1)
        val validTo = createInstant(2024, AUGUST, 31)

        assertEquals(AVAILABLE, metadata.copy(blockReason = null, validFrom = null, validTo = null).getTimeAvailability())
        assertEquals(AVAILABLE, metadata.copy(blockReason = null, validFrom = null, validTo = null).getTimeAvailability(atBeforeFrom))
        assertEquals(AVAILABLE, metadata.copy(blockReason = null, validFrom = validFrom, validTo = null).getTimeAvailability())
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = null, validFrom = validFrom, validTo = null).getTimeAvailability(atBeforeFrom))
        assertEquals(AVAILABLE, metadata.copy(blockReason = null, validFrom = validFrom, validTo = null).getTimeAvailability(atAfterTo))
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = null, validFrom = null, validTo = validTo).getTimeAvailability())
        assertEquals(AVAILABLE, metadata.copy(blockReason = null, validFrom = null, validTo = validTo).getTimeAvailability(atBeforeFrom))
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = null, validFrom = null, validTo = validTo).getTimeAvailability(atAfterTo))
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).getTimeAvailability())
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBeforeFrom))
        assertEquals(AVAILABLE, metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBetweenFromTo))
        assertEquals(
            NOT_AVAILABLE_ANYMORE,
            metadata.copy(blockReason = null, validFrom = validFrom, validTo = validTo).getTimeAvailability(atAfterTo)
        )

        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = null, validTo = null).getTimeAvailability())
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = null, validTo = null).getTimeAvailability(atBeforeFrom))
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = validFrom, validTo = null).getTimeAvailability())
        assertEquals(
            NOT_YET_AVAILABLE,
            metadata.copy(blockReason = STARTDATE, validFrom = validFrom, validTo = null).getTimeAvailability(atBeforeFrom)
        )
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = validFrom, validTo = null).getTimeAvailability(atAfterTo))
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = null, validTo = validTo).getTimeAvailability())
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = null, validTo = validTo).getTimeAvailability(atBeforeFrom))
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = null, validTo = validTo).getTimeAvailability(atAfterTo))
        assertEquals(NOT_YET_AVAILABLE, metadata.copy(blockReason = STARTDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability())
        assertEquals(
            NOT_YET_AVAILABLE,
            metadata.copy(blockReason = STARTDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBeforeFrom)
        )
        assertEquals(
            NOT_YET_AVAILABLE,
            metadata.copy(blockReason = STARTDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBetweenFromTo)
        )
        assertEquals(
            NOT_YET_AVAILABLE,
            metadata.copy(blockReason = STARTDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability(atAfterTo)
        )

        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = ENDDATE, validFrom = null, validTo = null).getTimeAvailability())
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = ENDDATE, validFrom = null, validTo = null).getTimeAvailability(atBeforeFrom))
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = ENDDATE, validFrom = validFrom, validTo = null).getTimeAvailability())
        assertEquals(
            NOT_AVAILABLE_ANYMORE,
            metadata.copy(blockReason = ENDDATE, validFrom = validFrom, validTo = null).getTimeAvailability(atBeforeFrom)
        )
        assertEquals(
            NOT_AVAILABLE_ANYMORE,
            metadata.copy(blockReason = ENDDATE, validFrom = validFrom, validTo = null).getTimeAvailability(atAfterTo)
        )
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = ENDDATE, validFrom = null, validTo = validTo).getTimeAvailability())
        assertEquals(
            NOT_AVAILABLE_ANYMORE,
            metadata.copy(blockReason = ENDDATE, validFrom = null, validTo = validTo).getTimeAvailability(atBeforeFrom)
        )
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = ENDDATE, validFrom = null, validTo = validTo).getTimeAvailability(atAfterTo))
        assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = ENDDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability())
        assertEquals(
            NOT_AVAILABLE_ANYMORE,
            metadata.copy(blockReason = ENDDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBeforeFrom)
        )
        assertEquals(
            NOT_AVAILABLE_ANYMORE,
            metadata.copy(blockReason = ENDDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBetweenFromTo)
        )
        assertEquals(
            NOT_AVAILABLE_ANYMORE,
            metadata.copy(blockReason = ENDDATE, validFrom = validFrom, validTo = validTo).getTimeAvailability(atAfterTo)
        )

        (BlockReason.entries - STARTDATE - ENDDATE).forEach { blockReason ->
            assertEquals(AVAILABLE, metadata.copy(blockReason = blockReason, validFrom = null, validTo = null).getTimeAvailability())
            assertEquals(AVAILABLE, metadata.copy(blockReason = blockReason, validFrom = null, validTo = null).getTimeAvailability(atBeforeFrom))
            assertEquals(AVAILABLE, metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = null).getTimeAvailability())
            assertEquals(
                NOT_YET_AVAILABLE,
                metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = null).getTimeAvailability(atBeforeFrom)
            )
            assertEquals(AVAILABLE, metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = null).getTimeAvailability(atAfterTo))
            assertEquals(NOT_AVAILABLE_ANYMORE, metadata.copy(blockReason = blockReason, validFrom = null, validTo = validTo).getTimeAvailability())
            assertEquals(AVAILABLE, metadata.copy(blockReason = blockReason, validFrom = null, validTo = validTo).getTimeAvailability(atBeforeFrom))
            assertEquals(
                NOT_AVAILABLE_ANYMORE,
                metadata.copy(blockReason = blockReason, validFrom = null, validTo = validTo).getTimeAvailability(atAfterTo)
            )
            assertEquals(
                NOT_AVAILABLE_ANYMORE,
                metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).getTimeAvailability()
            )
            assertEquals(
                NOT_YET_AVAILABLE,
                metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBeforeFrom)
            )
            assertEquals(
                AVAILABLE,
                metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).getTimeAvailability(atBetweenFromTo)
            )
            assertEquals(
                NOT_AVAILABLE_ANYMORE,
                metadata.copy(blockReason = blockReason, validFrom = validFrom, validTo = validTo).getTimeAvailability(atAfterTo)
            )
        }
    }

    @Test
    fun `is blocked valid from time`() {
        val metadata = createMediaMetadata()
        val atBeforeFrom = createInstant(2024, AUGUST, 15)
        val atAfterFrom = createInstant(2024, SEPTEMBER, 15)
        val validFrom = createInstant(2024, SEPTEMBER, 1)

        assertFalse(metadata.copy(validFrom = null).isBlockedValidFromTime())
        assertFalse(metadata.copy(validFrom = null).isBlockedValidFromTime(atBeforeFrom.toEpochMilliseconds()))
        assertFalse(metadata.copy(validFrom = null).isBlockedValidFromTime(validFrom.toEpochMilliseconds()))
        assertFalse(metadata.copy(validFrom = null).isBlockedValidFromTime(atAfterFrom.toEpochMilliseconds()))

        assertFalse(metadata.copy(validFrom = validFrom).isBlockedValidFromTime())
        assertTrue(metadata.copy(validFrom = validFrom).isBlockedValidFromTime(atBeforeFrom.toEpochMilliseconds()))
        assertFalse(metadata.copy(validFrom = validFrom).isBlockedValidFromTime(validFrom.toEpochMilliseconds()))
        assertFalse(metadata.copy(validFrom = validFrom).isBlockedValidFromTime(atAfterFrom.toEpochMilliseconds()))
    }

    @Test
    fun `is block valid to time`() {
        val metadata = createMediaMetadata()
        val atBeforeTo = createInstant(2024, AUGUST, 15)
        val atAfterTo = createInstant(2024, SEPTEMBER, 15)
        val validTo = createInstant(2024, SEPTEMBER, 1)

        assertFalse(metadata.copy(validTo = null).isBlockValidToTime())
        assertFalse(metadata.copy(validTo = null).isBlockValidToTime(atBeforeTo.toEpochMilliseconds()))
        assertFalse(metadata.copy(validTo = null).isBlockValidToTime(validTo.toEpochMilliseconds()))
        assertFalse(metadata.copy(validTo = null).isBlockValidToTime(atAfterTo.toEpochMilliseconds()))

        assertTrue(metadata.copy(validTo = validTo).isBlockValidToTime())
        assertFalse(metadata.copy(validTo = validTo).isBlockValidToTime(atBeforeTo.toEpochMilliseconds()))
        assertFalse(metadata.copy(validTo = validTo).isBlockValidToTime(validTo.toEpochMilliseconds()))
        assertTrue(metadata.copy(validTo = validTo).isBlockValidToTime(atAfterTo.toEpochMilliseconds()))
    }

    @Test
    fun `is live`() {
        val metadata = createMediaMetadata()

        assertFalse(metadata.copy(type = Type.EPISODE).isLive())
        assertFalse(metadata.copy(type = Type.EXTRACT).isLive())
        assertFalse(metadata.copy(type = Type.TRAILER).isLive())
        assertFalse(metadata.copy(type = Type.CLIP).isLive())
        assertTrue(metadata.copy(type = Type.LIVESTREAM).isLive())
        assertTrue(metadata.copy(type = Type.SCHEDULED_LIVESTREAM).isLive())
    }

    private data class SimpleSRGMediaMetadata(
        override val mediaType: MediaType,
        override val type: Type,
        override val date: Instant,
        override val duration: Long,
        override val blockReason: BlockReason? = null,
        override val validFrom: Instant? = null,
        override val validTo: Instant? = null,
        override val assignedBy: Referrer? = null,
        override val playableAbroad: Boolean,
        override val youthProtectionColor: YouthProtectionColor? = null,
        override val podcastSdUrl: String? = null,
        override val podcastHdUrl: String? = null,
        override val relatedContentList: List<RelatedContent>? = null,
        override val socialCountList: List<SocialCountEntry>? = null,
        override val aspectRatio: AspectRatio? = null,
        // SRGIdentifierMetadata
        override val urn: String,
        // ILObject
        override val id: String,
        override val vendor: Vendor,
        // SRGImageMetadata
        override val imageUrl: ImageUrl,
        override val imageTitle: String? = null,
        override val imageCopyright: String? = null,
        override val imageFocalPoint: FocalPoint? = null,
        // SRGMetadata
        override val title: String,
        override val lead: String? = null,
        override val description: String? = null,
    ) : SRGMediaMetadata

    private companion object {
        private const val HD_URL = "https://hd.url/"
        private const val SD_URL = "https://sd.url/"

        private fun createMediaMetadata(): SimpleSRGMediaMetadata {
            return SimpleSRGMediaMetadata(
                mediaType = MediaType.VIDEO,
                type = Type.EPISODE,
                date = Clock.System.now(),
                duration = 90.minutes.inWholeMilliseconds,
                playableAbroad = false,
                urn = "urn:rts:video:123456",
                id = "media-id",
                vendor = Vendor.RTS,
                imageUrl = ImageUrl("https://image.url/"),
                title = "media-title",
            )
        }

        private fun createInstant(year: Int, month: Int, day: Int): Instant {
            val timestamp = LocalDate(year, month, day)
                .atStartOfDayIn(TimeZone.currentSystemDefault())
                .toEpochMilliseconds()

            return Instant.fromEpochMilliseconds(timestamp)
        }
    }
}
