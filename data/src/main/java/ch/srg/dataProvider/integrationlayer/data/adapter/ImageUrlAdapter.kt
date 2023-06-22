package ch.srg.dataProvider.integrationlayer.data.adapter

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ImageUrlAdapter {
    @FromJson
    fun fromJson(imageUrl: String): ImageUrl {
        return ImageUrl(imageUrl)
    }

    @ToJson
    fun toJson(imageUrl: ImageUrl): String {
        return imageUrl.rawUrl
    }
}
