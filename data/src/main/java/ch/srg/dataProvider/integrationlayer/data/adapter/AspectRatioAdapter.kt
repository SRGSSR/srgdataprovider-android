package ch.srg.dataProvider.integrationlayer.data.adapter

import ch.srg.dataProvider.integrationlayer.data.remote.AspectRatio
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class AspectRatioAdapter {

    @ToJson
    fun toJson(aspectRatio: AspectRatio): String {
        return aspectRatio.toString()
    }

    @FromJson
    fun fromJson(json: String): AspectRatio {
        return AspectRatio.parse(json)
    }
}
