package ch.srg.dataProvider.integrationlayer.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ListResultTest {
    @Test
    fun `list result null data`() {
        val listResult = StringListResult(data = null)

        assertEquals(emptyList(), listResult.list)
        assertEquals(0, listResult.size)
        assertTrue(listResult.isEmpty())
        assertFalse(listResult.contains(""))
        assertFalse(listResult.contains("c"))
        assertTrue(listResult.containsAll(emptyList()))
        assertFalse(listResult.containsAll(listOf("b", "d")))
        assertNotNull(listResult.iterator())
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `list result null data with get item`() {
        val listResult = StringListResult(data = null)

        listResult[0]
    }

    @Test
    fun `list result empty data`() {
        val listResult = StringListResult(data = emptyList())

        assertEquals(emptyList(), listResult.list)
        assertEquals(0, listResult.size)
        assertTrue(listResult.isEmpty())
        assertFalse(listResult.contains(""))
        assertFalse(listResult.contains("c"))
        assertTrue(listResult.containsAll(emptyList()))
        assertFalse(listResult.containsAll(listOf("b", "d")))
        assertNotNull(listResult.iterator())
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `list result empty data with get item`() {
        val listResult = StringListResult(data = emptyList())

        listResult[0]
    }

    @Test
    fun `list result`() {
        val data = listOf("a", "b", "c", "d", "e", "f")
        val listResult = StringListResult(data = data)

        assertEquals(data, listResult.list)
        assertEquals(data.size, listResult.size)
        assertFalse(listResult.isEmpty())
        assertFalse(listResult.contains(""))
        assertTrue(listResult.contains("c"))
        assertTrue(listResult.containsAll(emptyList()))
        assertTrue(listResult.containsAll(listOf("b", "d")))
        assertFalse(listResult.containsAll(listOf("e", "g")))
        assertNotNull(listResult.iterator())
    }

    private class StringListResult(
        override val data: List<String>?,
    ) : ListResult<String>() {
        override val next = null
    }
}
