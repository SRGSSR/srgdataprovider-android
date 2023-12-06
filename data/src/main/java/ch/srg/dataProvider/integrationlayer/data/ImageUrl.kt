/*
 * Copyright (c) SRG SSR. All rights reserved.
 * License information is available from the LICENSE file.
 */
@file:Suppress("MemberVisibilityCanBePrivate")

package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.serializer.ImageUrlSerializer
import java.io.Serializable

/**
 * Image url
 *
 * @property rawUrl Internal image url, to retrieve the url use [ImageUrl.decorated].
 */
@Suppress("SerialVersionUIDInSerializableClass")
@kotlinx.serialization.Serializable(with = ImageUrlSerializer::class)
data class ImageUrl(
    /**
     * Only for internal use! Please use a Decorator!
     *
     * @return the undecorated url
     */
    val rawUrl: String
) : Serializable {

    /**
     * Decorated
     *
     * @param decorator The [ImageUrlDecorator] used to decorate the [rawUrl].
     * @param widthPixels The width of the image.
     * @return The decorated [rawUrl].
     */
    fun decorated(decorator: ImageUrlDecorator, widthPixels: Int): String {
        return decorator.decorate(rawUrl, widthPixels)
    }

    @Deprecated("Using toString is not recommended in this case.", replaceWith = ReplaceWith("rawUrl"))
    override fun toString(): String {
        return rawUrl
    }
}
