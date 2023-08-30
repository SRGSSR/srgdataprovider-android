package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
sealed class SearchResultList : ListResult<SearchResult>() {
    abstract val searchTerm: String?
    abstract val total: Int?

    fun urns(): List<String>? {
        return data?.map { it.urn }
    }
}

@Serializable
data class SearchResultMediaList(
    override val next: String?,
    @SerialName("searchResultMediaList")
    override val data: List<SearchResult>? = null,
    override val searchTerm: String? = null,
    override val total: Int? = null,
    val aggregations: MediaAggregations? = null,
    val exactMatchTotal: Int? = null,
    val suggestionList: List<SearchSuggestion>? = null
) : SearchResultList()

@Serializable
data class SearchResultShowList(
    override val next: String? = null,
    override val total: Int? = null,
    @SerialName("searchResultShowList")
    override val data: List<SearchResult>? = null,
    override val searchTerm: String? = null
) : SearchResultList()

@Serializable
data class SearchResultWithMediaList(
    override val next: String? = null,
    override val data: List<Media>? = null,
    val searchTerm: String? = null,
    val total: Int? = null,
    val aggregations: MediaAggregations? = null,
    val exactMatchTotal: Int? = null,
    val suggestionList: List<SearchSuggestion>? = null
) : ListResult<Media>()

@Serializable
data class SearchResultWithShowList(
    override val next: String? = null,
    val total: Int? = null,
    override val data: List<Show>? = null,
    val searchTerm: String? = null
) : ListResult<Show>()
