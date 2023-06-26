package ch.srg.dataProvider.integrationlayer.data;

import android.util.Rational;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class GsonSerializerTest {

    Gson gson;

    @Before
    public void initGson() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateJsonSerializer())
                .registerTypeAdapter(Rational.class, new RationalSerializer())
                .create();
    }

    @Test
    public void testDate() throws ParseException {
        String ilJsonDate = "\"2021-12-07T20:14:59+01:00\"";
        Calendar cal = new GregorianCalendar(2021, 11, 7, 20, 14, 59);
        Date date = cal.getTime();

        String jsonResult = gson.toJson(date);
        Assert.assertEquals(ilJsonDate, jsonResult);

        Date result = gson.fromJson(ilJsonDate, Date.class);
        Assert.assertEquals(date, result);
    }

    @Test
    public void testRational() throws ParseException {
        Rational rational = new Rational(16, 9);
        String ilJson = "\"16:9\"";

        String jsonResult = gson.toJson(rational);
        Assert.assertEquals(ilJson, jsonResult);

        Rational result = gson.fromJson(ilJson, Rational.class);
        Assert.assertEquals(rational, result);
    }
}
