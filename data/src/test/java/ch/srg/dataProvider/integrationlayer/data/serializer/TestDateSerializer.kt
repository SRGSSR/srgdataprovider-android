package ch.srg.dataProvider.integrationlayer.data.serializer

import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.srg.dataProvider.integrationlayer.data.DataProviderJson
import org.junit.runner.RunWith
import java.util.Date
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class TestDateSerializer {

    @Ignore("robolectric date parsing doesn't use same format")
    @Test
    fun testToJSon() {
        val expectedJson = "\"2017-05-30T08:36:15+02:00\""
        val input = Date(1496126175000L)
        assertEquals(expectedJson, DataProviderJson.encodeToString(serializer = DateSerializer(), input))
    }

    @Ignore("robolectric date parsing doesn't use same format")
    @Test
    fun testFromJson() {
        val input = "\"2017-05-30T08:36:15+02:00\""
        val expectedDate = Date(1496126175000L)
        assertEquals(expectedDate, DataProviderJson.decodeFromString(deserializer = DateSerializer(), input))
    }
}
