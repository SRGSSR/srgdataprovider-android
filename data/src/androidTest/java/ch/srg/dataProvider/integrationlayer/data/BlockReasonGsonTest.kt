package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.gson.BlockReasonJSonDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.junit.Assert
import org.junit.Test

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class BlockReasonGsonTest {

    private data class Dummy(val title: String, val blockReason: BlockReason?)

    private val gson: Gson = GsonBuilder()
            .registerTypeAdapter(BlockReason::class.java, BlockReasonJSonDeserializer())
            .create()

    @Test
    fun testNoBlockReason() {
        val expected = Dummy("NoBlockReason", null)
        val jsonObj = JsonObject().apply {
            addProperty("title", "NoBlockReason")
        }
        val result: Dummy = gson.fromJson(jsonObj, Dummy::class.java)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun testKnownBlockReason() {
        val expected = Dummy("KnownBlockReason", BlockReason.GEOBLOCK)
        val jsonObj = JsonObject().apply {
            addProperty("title", "KnownBlockReason")
            addProperty("blockReason", "GEOBLOCK")
        }
        val result: Dummy = gson.fromJson(jsonObj, Dummy::class.java)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun testUnknownBlockReason() {
        val expected = Dummy("NoBlockReason", BlockReason.UNKNOWN)
        val jsonObj = JsonObject().apply {
            addProperty("title", "NoBlockReason")
            addProperty("blockReason", "NEWBLOCKREASON")
        }
        val result: Dummy = gson.fromJson(jsonObj, Dummy::class.java)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun testKnownSerialization() {
        val expected = JsonObject().apply {
            addProperty("title", "SerializeGeoBlock")
            addProperty("blockReason", "GEOBLOCK")
        }
        val result = gson.toJsonTree(Dummy("SerializeGeoBlock", BlockReason.GEOBLOCK))
        Assert.assertEquals(expected, result)
    }

    @Test
    fun testNoBlockReasonSerialization() {
        val expected = JsonObject().apply {
            addProperty("title", "SerializeNoBlockReason")
        }
        val result = gson.toJsonTree(Dummy("SerializeNoBlockReason", null))
        Assert.assertEquals(expected, result)
    }
}
