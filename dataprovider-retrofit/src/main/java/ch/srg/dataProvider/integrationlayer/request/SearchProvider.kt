package ch.srg.dataProvider.integrationlayer.request

import ch.srg.dataProvider.integrationlayer.data.remote.MediaListResult
import ch.srg.dataProvider.integrationlayer.data.remote.SearchParams
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultMediaList
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultShowList
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultWithMediaList
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultWithShowList
import ch.srg.dataProvider.integrationlayer.data.remote.ShowListResult
import ch.srg.dataProvider.integrationlayer.request.parameters.Bu
import ch.srg.dataProvider.integrationlayer.request.parameters.IlMediaType
import ch.srg.dataProvider.integrationlayer.request.parameters.IlPaging
import ch.srg.dataProvider.integrationlayer.request.parameters.IlUrns

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Suppress("TooGenericExceptionCaught")
class SearchProvider(private val ilService: IlService) {

    /**
     * Search media result without Media object, only urns
     */
    suspend fun searchMediaResult(
        bu: Bu,
        searchTerm: String,
        params: SearchParams.MediaParams? = null,
        pageSize: IlPaging.Size? = null
    ): SearchResultMediaList {
        return ilService.getSearchMedias(bu, searchTerm, params?.toQueryMap(), pageSize)
    }

    /**
     * Search media result without Media object, only urns
     */
    suspend fun searchMediaResult(nextUrl: String): SearchResultMediaList {
        return ilService.getSearchMediaNextUrl(nextUrl)
    }

    suspend fun searchMedias(
        bu: Bu,
        searchTerm: String,
        params: SearchParams.MediaParams? = null,
        pageSize: IlPaging.Size? = null,
    ): SearchResultWithMediaList {
        return getSearchWithMediaResult(searchMediaResult(bu, searchTerm, params, pageSize))
    }

    suspend fun searchMediaWithNextUrl(nextUrl: String): SearchResultWithMediaList {
        return getSearchWithMediaResult(searchMediaResult(nextUrl))
    }

    private suspend fun getSearchWithMediaResult(searchResult: SearchResultMediaList): SearchResultWithMediaList {
        val mediaList: MediaListResult = if (searchResult.isEmpty()) {
            MediaListResult(emptyList())
        } else {
            try {
                ilService.getMediaListFromUrns(IlUrns.from(searchResult.list))
            } catch (e: Exception) {
                MediaListResult(emptyList())
            }
        }
        return SearchResultWithMediaList(
            next = searchResult.next,
            data = mediaList.list,
            searchTerm = searchResult.searchTerm,
            total = searchResult.total,
            aggregations = searchResult.aggregations,
            exactMatchTotal = searchResult.exactMatchTotal,
            suggestionList = searchResult.suggestionList
        )
    }

    /**
     * Search show result without Media object, only urns
     */
    suspend fun searchShowResult(
        bu: Bu,
        searchTerm: String,
        mediaType: IlMediaType? = null,
        pageSize: IlPaging? = null,
    ): SearchResultShowList {
        return ilService.getSearchShows(bu, searchTerm, mediaType, pageSize)
    }

    /**
     * Search show result without Media object, only urns
     */
    suspend fun searchShowResultNextUrl(nextUrl: String): SearchResultShowList {
        return ilService.getSearchShowNextUrl(nextUrl)
    }

    suspend fun searchShows(
        bu: Bu,
        searchTerm: String,
        mediaType: IlMediaType? = null,
        pageSize: IlPaging? = null,
    ): SearchResultWithShowList {
        return getSearchWithShowResult(searchShowResult(bu, searchTerm, mediaType, pageSize))
    }

    suspend fun searchShowWithNextUrl(nextUrl: String): SearchResultWithShowList {
        return getSearchWithShowResult(searchShowResultNextUrl(nextUrl))
    }

    private suspend fun getSearchWithShowResult(searchResult: SearchResultShowList): SearchResultWithShowList {
        val mediaList: ShowListResult = if (searchResult.list.isEmpty()) {
            ShowListResult(emptyList())
        } else {
            try {
                ilService.getShowListFromUrns(IlUrns.from(searchResult.list))
            } catch (e: Exception) {
                ShowListResult(emptyList())
            }
        }
        return SearchResultWithShowList(
            next = searchResult.next,
            data = mediaList.list,
            searchTerm = searchResult.searchTerm,
            total = searchResult.total
        )
    }
}
