@file:UseSerializers(DateSerializer::class)

package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Song(
    val isPlayingNow: Boolean,
    val date: Date,
    val title: String,
    val artist: Artist,
    val duration: Int? = null,
    val cd: Cd? = null
)

@Serializable
data class Cd(
    val name: String,
    val coverUrlSmall: String? = null,
    val coverUrlLarge: String? = null
)

@Serializable
data class Artist(val name: String, val url: String? = null)
