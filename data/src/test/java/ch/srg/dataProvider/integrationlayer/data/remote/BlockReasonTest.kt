package ch.srg.dataProvider.integrationlayer.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockReasonTest {
    @Test
    fun `parse value`() {
        assertEquals(BlockReason.UNKNOWN, BlockReason.parseValue(""))
        assertEquals(BlockReason.UNKNOWN, BlockReason.parseValue("INVALID_REASON"))

        BlockReason.entries.forEach { blockReason ->
            assertEquals(blockReason, BlockReason.parseValue(blockReason.name))
        }

        BlockReason.entries.forEach { blockReason ->
            assertEquals(BlockReason.UNKNOWN, BlockReason.parseValue(blockReason.name.lowercase()))
        }
    }
}
