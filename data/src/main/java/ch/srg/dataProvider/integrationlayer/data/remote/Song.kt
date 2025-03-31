package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Song(
    val isPlayingNow: Boolean,
    val date: Instant,
    val title: String,
    val artist: Artist,
    val duration: Int? = null,
    val cd: Cd? = null
)

@Serializable
data class Cd(
    val name: String,
    val coverUrlSmall: ImageUrl? = null,
    val coverUrlLarge: ImageUrl? = null
)

@Serializable
data class Artist(val name: String, val url: String? = null)
