package ch.srg.dataProvider.integrationlayer.data

import java.util.*

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Song(
        val isPlayingNow: Boolean,
        val date: Date,
        val title: String,
        val artist: Artist,
        val duration: Int? = null,
        val cd: Cd? = null
)

data class Cd(
        val name: String,
        val coverUrlSmall: ImageUrl? = null,
        val coverUrlLarge: ImageUrl? = null
)

data class Artist(val name: String, val url: String? = null)