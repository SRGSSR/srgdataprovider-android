package ch.srg.dataProvider.integrationlayer.data

import android.util.Rational

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class SpriteSheet(
        val urn: String,
        val rows: Int,
        val columns: Int,
        val thumbnailHeight: Int,
        val thumbnailWidth: Int,
        val interval: Long,
        val url: String
) {
    val count: Int = rows * columns
    val aspectRatio = Rational(thumbnailWidth, thumbnailHeight)
}