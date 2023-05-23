package ch.srg.dataProvider.integrationlayer.data.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

import ch.srg.dataProvider.integrationlayer.data.ISO8601DateParser;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class DateJsonSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        ISO8601DateParser dateParser = new ISO8601DateParser();
        return new JsonPrimitive(dateParser.format(src));
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dateString = json.getAsString();
        try {
            ISO8601DateParser dateParser = new ISO8601DateParser();
            return dateParser.parseDate(dateString);
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }
}