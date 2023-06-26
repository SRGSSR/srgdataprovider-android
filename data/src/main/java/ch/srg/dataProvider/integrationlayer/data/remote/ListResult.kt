package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
abstract class ListResult<T> : Iterable<T> {
    internal abstract val data: List<T>?
    abstract val next: String?

    val list: List<T>
        get() {
            return data.orEmpty()
        }

    val size: Int
        get() {
            return list.size
        }

    fun isEmpty(): Boolean {
        return data.isNullOrEmpty()
    }

    operator fun get(index: Int): T {
        return list[index]
    }

    fun contains(element: T): Boolean {
        return list.contains(element)
    }

    fun containsAll(elements: Collection<T>): Boolean {
        return list.containsAll(elements)
    }

    override fun iterator(): Iterator<T> {
        return list.iterator()
    }
}

@JsonClass(generateAdapter = true)
data class MediaListResult(
    @Json(name = "mediaList")
    override val data: List<Media>? = null,
    override val next: String? = null,
    val recommendationId: String? = null,
    val title: String? = null,
) : ListResult<Media>()

@JsonClass(generateAdapter = true)
data class ShowListResult(
    @Json(name = "showList")
    override val data: List<Show>? = emptyList(),
    override val next: String? = null,
) : ListResult<Show>()

@JsonClass(generateAdapter = true)
data class TopicListResult(
    @Json(name = "topicList")
    override val data: List<Topic>? = null,
    override val next: String? = null
) : ListResult<Topic>()

@JsonClass(generateAdapter = true)
data class ChannelListResult(
    @Json(name = "channelList")
    override val data: List<Channel>? = null,
    override val next: String? = null
) : ListResult<Channel>()

@JsonClass(generateAdapter = true)
data class MediaListWithShowResult(
    val show: Show?,
    @Json(name = "mediaList")
    override val data: List<Media>? = null,
    override val next: String? = null
) : ListResult<Media>()

@JsonClass(generateAdapter = true)
data class SongListResult(
    @Json(name = "songList")
    override val data: List<Song>? = null,
    override val next: String? = null,
) : ListResult<Song>()

@JsonClass(generateAdapter = true)
data class ProgramCompositionListResult(
    val channel: Channel,
    @Json(name = "programList")
    override val data: List<Program>?,
    override val next: String? = null
) : ListResult<Program>()

@JsonClass(generateAdapter = true)
data class EpisodeCompositionListResult(
    val show: Show,
    val channel: Channel? = null,
    @Json(name = "episodeList")
    override val data: List<Episode>? = null,
    override val next: String? = null
) : ListResult<Episode>()
