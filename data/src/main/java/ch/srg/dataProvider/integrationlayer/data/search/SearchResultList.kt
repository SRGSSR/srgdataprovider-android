package ch.srg.dataProvider.integrationlayer.data.search

import ch.srg.dataProvider.integrationlayer.data.ListResult
import ch.srg.dataProvider.integrationlayer.data.Media
import ch.srg.dataProvider.integrationlayer.data.Show
import com.google.gson.annotations.SerializedName

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

data class SearchResultMediaList(
    override val next: String?,
    @SerializedName("searchResultMediaList")
    override val data: List<SearchResult>? = null,
    override val searchTerm: String? = null,
    override val total: Int? = null,
    val aggregations: MediaAggregations? = null,
    val exactMatchTotal: Int? = null,
    val suggestionList: List<SearchSuggestion>? = null
) : SearchResultList()

data class SearchResultShowList(
    override val next: String? = null,
    override val total: Int? = null,
    @SerializedName("searchResultShowList")
    override val data: List<SearchResult>? = null,
    override val searchTerm: String? = null
) : SearchResultList()

data class SearchResultWithMediaList(
    override val next: String? = null,
    override val data: List<Media>? = null,
    val searchTerm: String? = null,
    val total: Int? = null,
    val aggregations: MediaAggregations? = null,
    val exactMatchTotal: Int? = null,
    val suggestionList: List<SearchSuggestion>? = null
) : ListResult<Media>()

data class SearchResultWithShowList(
    override val next: String? = null,
    val total: Int? = null,
    override val data: List<Show>? = null,
    val searchTerm: String? = null
) : ListResult<Show>()
