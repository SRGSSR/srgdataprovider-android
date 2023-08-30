package ch.srg.dataProvider.integrationlayer.data

import org.junit.Assert
import org.junit.Test
import java.text.ParseException
import java.util.Date

/**
 * [Testing Fundamentals](http://d.android.com/tools/testing/testing_android.html)
 */
class DateParserTest {
    @Test
    fun test8601() {
        val parser = ISO8601DateParser()
        Assert.assertEquals(Date(1496126175000L), parser.parseDate("2017-05-30T08:36:15.079+02:00"))
        Assert.assertEquals(Date(1496126175000L), parser.parseDate("2017-05-30T08:36:15+02:00"))
        Assert.assertEquals(Date(1496126175000L), parser.parseDate("2017-05-30T06:36:15Z"))
    }

    @Test(expected = ParseException::class)
    fun testEmptyString() {
        ISO8601DateParser().parseDate("")
    }

    @Test(expected = ParseException::class)
    fun testInvalidString() {
        ISO8601DateParser().parseDate("Not_Date")
    }

    @Test(expected = ParseException::class)
    fun testInvalidDateString() {
        ISO8601DateParser().parseDate("2017-05-30AB08:36:15.079+02:00")
    }
}
