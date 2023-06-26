package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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

@JsonClass(generateAdapter = true)
data class SearchResultMediaList(
    override val next: String?,
    @Json(name = "searchResultMediaList")
    override val data: List<SearchResult>? = null,
    override val searchTerm: String? = null,
    override val total: Int? = null,
    val aggregations: MediaAggregations? = null,
    val exactMatchTotal: Int? = null,
    val suggestionList: List<SearchSuggestion>? = null
) : SearchResultList()

@JsonClass(generateAdapter = true)
data class SearchResultShowList(
    override val next: String? = null,
    override val total: Int? = null,
    @Json(name = "searchResultShowList")
    override val data: List<SearchResult>? = null,
    override val searchTerm: String? = null
) : SearchResultList()

@JsonClass(generateAdapter = true)
data class SearchResultWithMediaList(
    override val next: String? = null,
    override val data: List<Media>? = null,
    val searchTerm: String? = null,
    val total: Int? = null,
    val aggregations: MediaAggregations? = null,
    val exactMatchTotal: Int? = null,
    val suggestionList: List<SearchSuggestion>? = null
) : ListResult<Media>()

@JsonClass(generateAdapter = true)
data class SearchResultWithShowList(
    override val next: String? = null,
    val total: Int? = null,
    override val data: List<Show>? = null,
    val searchTerm: String? = null
) : ListResult<Show>()
