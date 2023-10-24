@file:Suppress("MemberVisibilityCanBePrivate")

package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.serializer.ImageUrlSerializer
import java.io.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
@Suppress("SerialVersionUIDInSerializableClass")
@kotlinx.serialization.Serializable(with = ImageUrlSerializer::class)
data class ImageUrl(
    /**
     * Only for internal use!
     *
     * @return the undecorated url
     */
    internal val rawUrl: String
) : Serializable {

    /**
     * Url
     *
     * @param decorator The [ImageUrlDecorator] used to decorate the [rawUrl].
     * @param widthPixels The width of the image.
     * @return The decorated [rawUrl].
     */
    fun url(decorator: ImageUrlDecorator, widthPixels: Int): String {
        return decorator.decorate(rawUrl, widthPixels)
    }

    override fun toString(): String {
        return rawUrl
    }
}

interface ImageUrlDecorator {
    fun decorate(source: String, widthPixels: Int): String
}
