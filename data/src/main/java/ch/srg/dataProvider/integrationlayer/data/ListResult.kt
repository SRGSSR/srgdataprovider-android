package ch.srg.dataProvider.integrationlayer.data

import com.google.gson.annotations.SerializedName

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
abstract class ListResult<T> : Iterable<T> {
    protected abstract val data: List<T>?
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

data class MediaListResult(
        @SerializedName("mediaList")
        override val data: List<Media>? = null,
        override val next: String? = null,
        val recommendationId: String? = null,
        val title: String? = null,
) : ListResult<Media>()

data class ShowListResult(
        @SerializedName("showList")
        override val data: List<Show>? = emptyList(),
        override val next: String? = null,
) : ListResult<Show>()

data class TopicListResult(
        @SerializedName("topicList")
        override val data: List<Topic>? = null,
        override val next: String? = null
) : ListResult<Topic>()

data class ChannelListResult(
        @SerializedName("channelList")
        override val data: List<Channel>? = null,
        override val next: String? = null
) : ListResult<Channel>()

data class MediaListWithShowResult(
        val show: Show?,
        @SerializedName("mediaList")
        override val data: List<Media>? = null,
        override val next: String? = null
) : ListResult<Media>()

data class SongListResult(
        @SerializedName("songList")
        override val data: List<Song>? = null,
        override val next: String? = null,
) : ListResult<Song>()

data class ProgramCompositionListResult(
        val channel: Channel,
        @SerializedName("programList")
        override val data: List<Program>?,
        override val next: String? = null
) : ListResult<Program>()

data class EpisodeCompositionListResult(
        val show: Show,
        val channel: Channel? = null,
        @SerializedName("episodeList")
        override val data: List<Episode>? = null,
        override val next: String? = null
) : ListResult<Episode>()