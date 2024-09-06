package ch.srg.dataProvider.integrationlayer.data.remote

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchParamsTest {
    @Test
    fun `MediaParams to default query map`() {
        val mediaParams = SearchParams.MediaParams()
        val queryMap = mediaParams.toQueryMap()

        assertTrue(queryMap.isEmpty())
    }

    @Test
    fun `MediaParams to query map`() {
        val mediaParams = SearchParams.MediaParams(
            mediaType = MediaType.VIDEO,
            topicUrns = listOf("urn:rts:video:123456", "urn:rts:video:456789"),
            showUrns = listOf("urn:rts:show:123456", "urn:rts:show:456789"),
            subtitlesAvailable = true,
            downloadAvailable = false,
            playableAbroad = true,
            quality = Quality.HD,
            durationFromInMinutes = 45,
            durationToInMinutes = 90,
            publishedDateFrom = "2024-09-01",
            publishedDateTo = "2024-09-30",
            includeAggregations = true,
            includeSuggestions = false,
            enableFuzzySearch = true,
            operator = SearchParams.Operator.OR,
            sortBy = SearchParams.SortBy.DATE,
            sortDir = SearchParams.SortDir.ASC,
        )
        val queryMap = mediaParams.toQueryMap()

        assertEquals(17, queryMap.size)
        assertEquals("VIDEO", queryMap["mediaType"])
        assertEquals("urn:rts:video:123456,urn:rts:video:456789", queryMap["topicUrns"])
        assertEquals("urn:rts:show:123456,urn:rts:show:456789", queryMap["showUrns"])
        assertEquals("true", queryMap["subtitlesAvailable"])
        assertEquals("false", queryMap["downloadAvailable"])
        assertEquals("true", queryMap["playableAbroad"])
        assertEquals("HD", queryMap["quality"])
        assertEquals("45", queryMap["durationFromInMinutes"])
        assertEquals("90", queryMap["durationToInMinutes"])
        assertEquals("2024-09-01", queryMap["publishedDateFrom"])
        assertEquals("2024-09-30", queryMap["publishedDateTo"])
        assertEquals("true", queryMap["includeAggregations"])
        assertEquals("false", queryMap["includeSuggestions"])
        assertEquals("true", queryMap["enableFuzzySearch"])
        assertEquals("or", queryMap["operator"])
        assertEquals("date", queryMap["sortBy"])
        assertEquals("asc", queryMap["sortDir"])
    }

    @Test
    fun `ShowParams to default query map`() {
        val showParams = SearchParams.ShowParams()
        val queryMap = showParams.toQueryMap()

        assertTrue(queryMap.isEmpty())
    }

    @Test
    fun `ShowParams to query map`() {
        val showParams = SearchParams.ShowParams(
            mediaType = MediaType.VIDEO,
        )
        val queryMap = showParams.toQueryMap()

        assertEquals(1, queryMap.size)
        assertEquals("VIDEO", queryMap["mediaType"])
    }
}
