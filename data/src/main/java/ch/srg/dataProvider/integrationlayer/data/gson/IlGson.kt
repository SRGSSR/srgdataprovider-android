package ch.srg.dataProvider.integrationlayer.data.gson

import android.util.Rational
import ch.srg.dataProvider.integrationlayer.data.BlockReason
import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import ch.srg.dataProvider.integrationlayer.gson.BlockReasonJSonDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.Date

object IlGson {

    @JvmStatic
    fun createBuilder(): GsonBuilder {
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java, DateJsonSerializer())
            .registerTypeAdapter(Rational::class.java, RationalSerializer())
            .registerTypeAdapter(ImageUrl::class.java, ImageUrlJsonSerializer())
            .registerTypeAdapter(BlockReason::class.java, BlockReasonJSonDeserializer())
    }


    @JvmStatic
    fun createGson(builder: GsonBuilder): Gson {
        return builder.create()
    }

    /**
     * Gson instance to use for parsing data received from integration layer
     */
    val gson = createGson(createBuilder())
}