package ch.srg.dataProvider.integrationlayer.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class TestDateSerializer {

    @Ignore("robolectric date parsing doesn't use same format")
    @Test
    fun testToJSon() {
        val expectedJson = "\"2017-05-30T08:36:15+02:00\""
        val input = Date(1496126175000L)
        Assert.assertEquals(expectedJson, DataProviderJson.encodeToString(serializer = DateSerializer(), input))
    }

    @Ignore("robolectric date parsing doesn't use same format")
    @Test
    fun testFromJson() {
        val input = "\"2017-05-30T08:36:15+02:00\""
        val expectedDate = Date(1496126175000L)
        Assert.assertEquals(expectedDate, DataProviderJson.decodeFromString(deserializer = DateSerializer(), input))
    }
}
