package ch.srg.dataProvider.integrationlayer.data.content

import ch.srg.dataProvider.integrationlayer.data.FocalPoint
import ch.srg.dataProvider.integrationlayer.data.ImageUrl

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Representation(
    /**
     * @see RepresentationType
     */
    val name: String,
    val properties: Properties? = Properties()
) {
    val title: String?
        get() = properties?.title

    val description: String?
        get() = properties?.description

    val label: String?
        get() = properties?.label

    val hasDetailPage: Boolean
        get() = properties?.hasDetailPage == true

    val pickRandomElement: Boolean
        get() = properties?.pickRandomElement == true

    val imageUrl: ImageUrl?
        get() = properties?.imageUrl

    val imageFocalPoint: FocalPoint?
        get() = properties?.imageFocalPoint

    data class Properties(
        val title: String? = null,
        val description: String? = null,
        val label: String? = null,
        val hasDetailPage: Boolean? = null,
        val pickRandomElement: Boolean? = null,
        val imageUrl: ImageUrl? = null,
        val imageFocalPoint: FocalPoint? = null
    )
}
