package ch.srg.dataProvider.integrationlayer.data.remote

import android.util.Rational
import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
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
