package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
abstract class ListResult<T> : Iterable<T> {
    abstract val data: List<T>?
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

@Serializable
data class MediaListResult(
    @SerialName("mediaList")
    override val data: List<Media>? = null,
    override val next: String? = null,
    val recommendationId: String? = null,
    val title: String? = null,
) : ListResult<Media>()

@Serializable
data class ShowListResult(
    @SerialName("showList")
    override val data: List<Show>? = emptyList(),
    override val next: String? = null,
) : ListResult<Show>()

@Serializable
data class TopicListResult(
    @SerialName("topicList")
    override val data: List<Topic>? = null,
    override val next: String? = null
) : ListResult<Topic>()

@Serializable
data class ChannelListResult(
    @SerialName("channelList")
    override val data: List<Channel>? = null,
    override val next: String? = null
) : ListResult<Channel>()

@Serializable
data class MediaListWithShowResult(
    val show: Show?,
    @SerialName("mediaList")
    override val data: List<Media>? = null,
    override val next: String? = null
) : ListResult<Media>()

@Serializable
data class SongListResult(
    @SerialName("songList")
    override val data: List<Song>? = null,
    override val next: String? = null,
) : ListResult<Song>()

@Serializable
data class ProgramCompositionListResult(
    val channel: Channel,
    @SerialName("programList")
    override val data: List<Program>?,
    override val next: String? = null
) : ListResult<Program>()

@Serializable
data class EpisodeCompositionListResult(
    val show: Show,
    val channel: Channel? = null,
    @SerialName("episodeList")
    override val data: List<Episode>? = null,
    override val next: String? = null
) : ListResult<Episode>()
