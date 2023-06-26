package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.adapter.BlockReasonAdapter
import ch.srg.dataProvider.integrationlayer.data.remote.BlockReason
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TestBlockReasonAdapter {

    private lateinit var adapter: BlockReasonAdapter

    @Before
    fun setup() {
        adapter = BlockReasonAdapter()
    }

    @Test
    fun testToJson() {
        val expectedJson  = "AGERATING18"
        val input = BlockReason.AGERATING18
        val actual = BlockReason.AGERATING18.toString()
        assertEquals(expectedJson, adapter.toJson(input))
    }

    @Test
    fun testFromJsonValidValue() {
        val json = "AGERATING18"
        val expectedBlockReason = BlockReason.AGERATING18
        assertEquals(expectedBlockReason, adapter.fromJson(json))
    }

    @Test
    fun testFromJsonUnknownValue() {
        val json = "SomethingNew"
        val expectedBlockReason = BlockReason.UNKNOWN
        assertEquals(expectedBlockReason, adapter.fromJson(json))
    }
}
