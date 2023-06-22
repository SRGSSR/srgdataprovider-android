@file:Suppress("MemberVisibilityCanBePrivate")

package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.IlImage.Scaling
import java.io.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
@Suppress("SerialVersionUIDInSerializableClass")
data class ImageUrl(
    /**
     * Only for internal use!
     *
     * @return the undecorated url
     */
    val rawUrl: String
) : Serializable {

    @JvmOverloads
    fun getIlImage(scaling: Scaling = Scaling.Default): IlImage {
        return IlImage(rawUrl, scaling)
    }

    override fun toString(): String {
        return rawUrl
    }
}
