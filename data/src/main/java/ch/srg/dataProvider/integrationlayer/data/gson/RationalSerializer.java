package ch.srg.dataProvider.integrationlayer.data.gson;

import android.util.Rational;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class RationalSerializer implements JsonSerializer<Rational>, JsonDeserializer<Rational> {

    @Override
    public JsonElement serialize(Rational src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getNumerator() + ":" + src.getDenominator());
    }

    @Override
    public Rational deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String valueString = json.getAsString();
        try {
            return Rational.parseRational(valueString);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
}
