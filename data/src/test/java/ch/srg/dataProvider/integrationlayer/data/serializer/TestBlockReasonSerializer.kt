package ch.srg.dataProvider.integrationlayer.data.serializer

import ch.srg.dataProvider.integrationlayer.data.DataProviderJson
import ch.srg.dataProvider.integrationlayer.data.remote.BlockReason
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class TestBlockReasonSerializer {

    @Test
    fun testToJson() {
        val expectedJson = "\"AGERATING18\""
        val input = BlockReason.AGERATING18
        assertEquals(expectedJson, DataProviderJson.encodeToString(input))
    }

    @Test
    fun testFromJsonValidValue() {
        val json = "\"AGERATING18\""
        val expectedBlockReason = BlockReason.AGERATING18
        assertEquals(expectedBlockReason, DataProviderJson.decodeFromString<BlockReason>(json))
    }

    @Test
    fun testFromJsonUnknownValue() {
        val json = "\"SomethingNew\""
        val expectedBlockReason = BlockReason.UNKNOWN
        assertEquals(expectedBlockReason, DataProviderJson.decodeFromString<BlockReason>(json))
    }
}
