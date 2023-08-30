package ch.srg.dataProvider.integrationlayer.data

import kotlinx.serialization.encodeToString
import org.junit.Assert.assertEquals
import org.junit.Test

class TestImageUrlSerializer {

    @Test
    fun testToJson() {
        val expectedJson = "\"https://image.url/image.png\""
        val input = ImageUrl("https://image.url/image.png")

        assertEquals(expectedJson, DataProviderJson.encodeToString(input))
    }

    @Test
    fun testFromJson() {
        val inputJson = "\"https://image.url/image.png\""
        val expectedImageUrl = ImageUrl("https://image.url/image.png")
        assertEquals(expectedImageUrl, DataProviderJson.decodeFromString<ImageUrl>(inputJson))
    }
}
