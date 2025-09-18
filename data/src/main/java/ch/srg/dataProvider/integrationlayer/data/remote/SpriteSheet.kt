package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class SpriteSheet(
    val urn: String,
    val rows: Int,
    val columns: Int,
    val thumbnailHeight: Int,
    val thumbnailWidth: Int,
    val interval: Long,
    val url: String
) {
    @Transient
    val count: Int = rows * columns

    @Transient
    val aspectRatio = AspectRatio(thumbnailWidth, thumbnailHeight)
}
