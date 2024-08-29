package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.remote.AspectRatio
import kotlinx.serialization.encodeToString
import org.junit.Assert.assertEquals
import org.junit.Test

class TestAspectRatioSerializer {

    @Test
    fun testToJson() {
        val expectedJson = "\"16:9\""
        val input = AspectRatio(16, 9)
        val result: String = DataProviderJson.encodeToString(input)
        assertEquals(expectedJson, result)
    }

    @Test
    fun testFromJsonValidValue() {
        val json = "\"16:9\""
        val expectedAspectRatio = AspectRatio(16, 9)
        val actualAspectRatio: AspectRatio = DataProviderJson.decodeFromString(json)
        assertEquals(expectedAspectRatio, actualAspectRatio)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonValidValueInvalidFormat() {
        val json = "\"16:9:8\""
        DataProviderJson.decodeFromString<AspectRatio>(json)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonInvalidSeparator() {
        val json = "\"16/9\""
        DataProviderJson.decodeFromString<AspectRatio>(json)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonInvalidString() {
        val json = "\"AspectRatio\""
        DataProviderJson.decodeFromString<AspectRatio>(json)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testFromJsonInvalidStringWithSeparator() {
        val json = "\"AspectRatio = 1:2\""
        DataProviderJson.decodeFromString<AspectRatio>(json)
    }

    @Test
    fun testFromJsonInfinity() {
        val json = "\"1:0\""
        val actualAspectRatio = DataProviderJson.decodeFromString<AspectRatio>(json)
        assertEquals(AspectRatio.Infinity, actualAspectRatio)
    }

    @Test
    fun testFromJsonZero() {
        val json = "\"0:12\""
        val actualAspectRatio = DataProviderJson.decodeFromString<AspectRatio>(json)
        assertEquals(AspectRatio.Zero, actualAspectRatio)
    }
}
