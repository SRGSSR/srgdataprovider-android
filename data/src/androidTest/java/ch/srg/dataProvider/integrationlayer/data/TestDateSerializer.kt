package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import org.junit.Assert
import org.junit.Test
import java.util.Date

class TestDateSerializer {

    @Test
    fun testToJSon() {
        val expectedJson = "\"2017-05-30T08:36:15+02:00\""
        val input = Date(1496126175000L)
        Assert.assertEquals(expectedJson, DataProviderJson.encodeToString(serializer = DateSerializer(), input))
    }

    @Test
    fun testFromJson() {
        val input = "\"2017-05-30T08:36:15+02:00\""
        val expectedDate = Date(1496126175000L)
        Assert.assertEquals(expectedDate, DataProviderJson.decodeFromString(deserializer = DateSerializer(), input))
    }
}
