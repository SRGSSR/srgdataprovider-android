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
    val rawUrl: String
) : Serializable {

    @JvmOverloads
    fun getIlImage(): IlImage {
        return IlImage(rawUrl)
    }

    override fun toString(): String {
        return rawUrl
    }
}
