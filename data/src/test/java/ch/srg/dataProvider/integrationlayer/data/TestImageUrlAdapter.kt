package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.adapter.ImageUrlAdapter
import org.junit.Assert.assertEquals
import org.junit.Test

class TestImageUrlAdapter {

    @Test
    fun testToJson() {
        val adapter = ImageUrlAdapter()
        val expectedUrl = "https://image.url/image.png"
        val input = ImageUrl(expectedUrl)

        assertEquals(expectedUrl, adapter.toJson(input))
    }

    @Test
    fun testFromJson() {
        val adapter = ImageUrlAdapter()
        val url = "https://image.url/image.png"
        val expectedImageUrl = ImageUrl(url)
        assertEquals(expectedImageUrl, adapter.fromJson(url))
    }
}
