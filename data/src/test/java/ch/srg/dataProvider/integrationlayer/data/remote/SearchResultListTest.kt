package ch.srg.dataProvider.integrationlayer.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SearchResultListTest {
    @Test
    fun `urns with null data`() {
        val searchResultList = SearchResultShowList(data = null)

        assertNull(searchResultList.urns())
    }

    @Test
    fun `urns with empty data`() {
        val searchResultList = SearchResultShowList(data = emptyList())

        assertEquals(emptyList(), searchResultList.urns())
    }

    @Test
    fun `urns with non-empty data`() {
        val data = listOf(
            SearchResult(id = "result-id-1", vendor = Vendor.RTS, urn = "urn:rts:video:1"),
            SearchResult(id = "result-id-2", vendor = Vendor.RTS, urn = "urn:rts:video:2"),
            SearchResult(id = "result-id-3", vendor = Vendor.RTS, urn = "urn:rts:video:3"),
        )
        val searchResultList = SearchResultShowList(data = data)

        assertEquals(data.map { it.urn }, searchResultList.urns())
    }
}
