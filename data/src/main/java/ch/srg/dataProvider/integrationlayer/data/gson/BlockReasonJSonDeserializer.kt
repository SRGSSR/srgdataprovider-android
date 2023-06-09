package ch.srg.dataProvider.integrationlayer.data.gson

import ch.srg.dataProvider.integrationlayer.data.BlockReason
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class BlockReasonJSonDeserializer : JsonDeserializer<BlockReason> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BlockReason {
        return BlockReason.parseValue(json.asString)
    }
}
