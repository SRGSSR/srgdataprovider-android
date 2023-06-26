package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.adapter.AspectRatioAdapter
import ch.srg.dataProvider.integrationlayer.data.remote.AspectRatio
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TestAspectRatioAdapter {

    private lateinit var adapter: AspectRatioAdapter

    @Before
    fun setup() {
        adapter = AspectRatioAdapter()
    }

    @Test
    fun testToJson() {
        val expectedJson = "16:9"
        val input = AspectRatio(16, 9)

        assertEquals(expectedJson, adapter.toJson(input))
    }

    @Test
    fun testFromJsonValidValue() {
        val json = "16:9"
        val expectedAspectRatio = AspectRatio(16, 9)
        assertEquals(expectedAspectRatio, adapter.fromJson(json))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonValidValueInvalidFormat() {
        val json = "16:9:8"
        adapter.fromJson(json)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonInvalidSeparator() {
        val json = "16/9"
        adapter.fromJson(json)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonInvalidString() {
        val json = "AspectRatio"
        adapter.fromJson(json)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonInvalidStringWithSeparator() {
        val json = "AspectRatio = 1:2"
        adapter.fromJson(json)
    }

    @Test
    fun testFromJsonInfinity() {
        val json = "1:0"
        val actualAspectRatio = adapter.fromJson(json)
        assertEquals(AspectRatio.Infinity, actualAspectRatio)
    }

    @Test
    fun testFromJsonZero() {
        val json = "0:12"
        val actualAspectRatio = adapter.fromJson(json)
        assertEquals(AspectRatio.Zero, actualAspectRatio)
    }
}
