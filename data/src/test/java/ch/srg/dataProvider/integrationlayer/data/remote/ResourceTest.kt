package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.remote.Resource.Drm.Type.FAIRPLAY
import ch.srg.dataProvider.integrationlayer.data.remote.VariantSource.DASH
import ch.srg.dataProvider.integrationlayer.data.remote.VariantType.SDH
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResourceTest {
    @Test
    fun `stream type`() {
        val resource = createResource()

        assertEquals(StreamType.ON_DEMAND, resource.copy(dvr = false, live = false).streamType)
        assertEquals(StreamType.LIVE, resource.copy(dvr = false, live = true).streamType)
        assertEquals(StreamType.LIVE_WITH_DVR, resource.copy(dvr = true, live = false).streamType)
        assertEquals(StreamType.LIVE_WITH_DVR, resource.copy(dvr = true, live = true).streamType)
    }

    @Test
    fun `is local file`() {
        val resource = createResource()

        assertFalse(resource.copy(url = "").isLocalFile())
        assertFalse(resource.copy(url = "http://www.srgssr.ch/").isLocalFile())
        assertFalse(resource.copy(url = "https://www.srgssr.ch/").isLocalFile())
        assertTrue(resource.copy(url = "file:///etc/hosts").isLocalFile())
    }

    @Test
    fun `has drm`() {
        val resource = createResource()

        assertFalse(resource.copy(drmList = null).hasDrm())
        assertFalse(resource.copy(drmList = emptyList()).hasDrm())
        assertTrue(resource.copy(drmList = listOf(Resource.Drm(type = FAIRPLAY, licenseUrl = "", certificateUrl = null))).hasDrm())
    }

    @Test
    fun `has subtitles`() {
        val resource = createResource()

        assertFalse(resource.copy(subtitleVariants = null).hasSubtitles())
        assertFalse(resource.copy(subtitleVariants = emptyList()).hasSubtitles())
        assertTrue(resource.copy(subtitleVariants = listOf(Variant(locale = "fr_CH", source = DASH, language = "fr", type = SDH))).hasSubtitles())
    }

    @Test
    fun `has audio tracks`() {
        val resource = createResource()

        assertFalse(resource.copy(audioVariants = null).hasAudioTracks())
        assertFalse(resource.copy(audioVariants = emptyList()).hasAudioTracks())
        assertTrue(resource.copy(audioVariants = listOf(Variant(locale = "fr_CH", source = DASH, language = "fr", type = SDH))).hasAudioTracks())
    }

    private companion object {
        private fun createResource(): Resource {
            return Resource(
                url = "https://url.com/resource",
                quality = Quality.HD,
                streamingMethod = StreamingMethod.DASH,
            )
        }
    }
}
