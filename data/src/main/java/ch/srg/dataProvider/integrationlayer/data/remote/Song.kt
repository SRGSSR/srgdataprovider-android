package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import com.squareup.moshi.JsonClass
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class Song(
    val isPlayingNow: Boolean,
    val date: Date,
    val title: String,
    val artist: Artist,
    val duration: Int? = null,
    val cd: Cd? = null
)

@JsonClass(generateAdapter = true)
data class Cd(
    val name: String,
    val coverUrlSmall: ImageUrl? = null,
    val coverUrlLarge: ImageUrl? = null
)

@JsonClass(generateAdapter = true)
data class Artist(val name: String, val url: String? = null)
