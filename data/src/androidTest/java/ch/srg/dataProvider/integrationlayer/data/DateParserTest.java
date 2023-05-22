package ch.srg.dataProvider.integrationlayer.data;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class DateParserTest {
    @Test
    public void test8601() throws ParseException {
        ISO8601DateParser parser = new ISO8601DateParser();
        Assert.assertEquals(new Date(1496126175000L), parser.parseDate("2017-05-30T08:36:15.079+02:00"));
        Assert.assertEquals(new Date(1496126175000L), parser.parseDate("2017-05-30T08:36:15+02:00"));
        Assert.assertEquals(new Date(1496126175000L), parser.parseDate("2017-05-30T06:36:15Z"));
    }
}