package ch.srg.dataProvider.integrationlayer.data.gson;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import ch.srg.dataProvider.integrationlayer.data.ImageUrl;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class ImageUrlJsonSerializer implements JsonSerializer<ImageUrl>, JsonDeserializer<ImageUrl> {

    @Override
    public ImageUrl deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String imageUrl = json.getAsString();
        if (TextUtils.isEmpty(imageUrl)) {
            return null;
        }
        return new ImageUrl(imageUrl);
    }

    @Override
    public JsonElement serialize(ImageUrl src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getIlImage().getUrl());
    }
}
