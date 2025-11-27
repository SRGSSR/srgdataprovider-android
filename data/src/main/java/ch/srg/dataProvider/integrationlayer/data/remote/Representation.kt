package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
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

    val imageUrl: String?
        get() = properties?.imageUrl

    val imageFocalPoint: FocalPoint?
        get() = properties?.imageFocalPoint

    val link: Link?
        get() = properties?.link

    @Serializable
    data class Properties(
        val title: String? = null,
        val description: String? = null,
        val label: String? = null,
        val hasDetailPage: Boolean? = null,
        val pickRandomElement: Boolean? = null,
        val imageUrl: String? = null,
        val imageFocalPoint: FocalPoint? = null,
        val link: Link? = null,
    )
}
