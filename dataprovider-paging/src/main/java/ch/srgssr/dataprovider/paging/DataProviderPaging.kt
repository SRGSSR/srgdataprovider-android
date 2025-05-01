package ch.srgssr.dataprovider.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ch.srg.dataProvider.integrationlayer.data.remote.Episode
import ch.srg.dataProvider.integrationlayer.data.remote.EventType
import ch.srg.dataProvider.integrationlayer.data.remote.ListResult
import ch.srg.dataProvider.integrationlayer.data.remote.LiveCenterType
import ch.srg.dataProvider.integrationlayer.data.remote.Media
import ch.srg.dataProvider.integrationlayer.data.remote.MediaFilter
import ch.srg.dataProvider.integrationlayer.data.remote.MediaType
import ch.srg.dataProvider.integrationlayer.data.remote.SearchParams
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultWithMediaList
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultWithShowList
import ch.srg.dataProvider.integrationlayer.data.remote.Show
import ch.srg.dataProvider.integrationlayer.data.remote.Song
import ch.srg.dataProvider.integrationlayer.data.remote.Transmission
import ch.srg.dataProvider.integrationlayer.request.IlService
import ch.srg.dataProvider.integrationlayer.request.SearchProvider
import ch.srg.dataProvider.integrationlayer.request.parameters.Bu
import ch.srg.dataProvider.integrationlayer.request.parameters.IlDate
import ch.srg.dataProvider.integrationlayer.request.parameters.IlDateTime
import ch.srg.dataProvider.integrationlayer.request.parameters.IlEventType
import ch.srg.dataProvider.integrationlayer.request.parameters.IlMediaType
import ch.srg.dataProvider.integrationlayer.request.parameters.IlPaging.Unlimited.toIlPaging
import ch.srg.dataProvider.integrationlayer.request.parameters.IlTransmission
import ch.srg.dataProvider.integrationlayer.request.parameters.IlUrns
import ch.srgssr.dataprovider.paging.datasource.NextUrlPagingSource
import ch.srgssr.dataprovider.paging.datasource.UrnsPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.Date

/**
 * Paging data source
 */
class DataProviderPaging(
    private val ilService: IlService
) {
    private val searchProvider = SearchProvider(ilService)

    fun getShowListFromUrns(tabUrns: List<String>, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Show>> {
        return Pager(config = pageSize.toPagingConfig(), pagingSourceFactory = {
            UrnsPagingSource(urns = tabUrns, call = { urns ->
                ilService.getShowListFromUrns(IlUrns(urns))
            })
        }).flow
    }

    fun getMediaListFromUrns(tabUrns: List<String>, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return Pager(config = pageSize.toPagingConfig(), pagingSourceFactory = {
            UrnsPagingSource(urns = tabUrns, call = { urns ->
                ilService.getMediaListFromUrns(IlUrns(urns))
            })
        }).flow
    }

    fun getLatestMediaByShowUrn(showUrn: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize, initialCall = { ilService.getLatestMediaByShowUrn(showUrn, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getLatestMediaByShowUrn(
        listShowUrns: List<String>,
        filter: MediaFilter? = null,
        maxPublishDate: Date? = null,
        minPublishDate: Date? = null,
        types: String? = null,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Media>> {
        return Pager(config = pageSize.toPagingConfig(), pagingSourceFactory = {
            UrnsPagingSource(
                urns = listShowUrns,
                call = { urns ->
                    ilService.getLatestMediaByShowUrns(
                        showUrns = IlUrns(urns),
                        onlyEpisodes = if (filter == MediaFilter.EPISODE_ONLY) true else null,
                        excludeEpisodes = if (filter == MediaFilter.EPISODE_EXCLUDED) true else null,
                        maxPublishedDate = maxPublishDate?.let { IlDateTime(it) },
                        minPublishedDate = minPublishDate?.let { IlDateTime(it) },
                        types = types
                    )
                }
            )
        }).flow
    }

    fun getLatestMediaByTopicUrn(topicUrn: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getLatestMediaByTopicUrn(topicUrn, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getMostClickedMediaByTopicUrn(topicUrn: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getMostClickedMediaByTopicUrn(topicUrn, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getTvMostClickedMedias(bu: Bu, topicId: String? = null, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getTvMostClickedMedias(bu, topicId, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getTvSoonExpiringMedias(bu: Bu, topicId: String? = null, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getTvSoonExpiringMedias(bu, topicId, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getTvSoonExpiringMedias(bu: Bu, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getTvWebFirstMedias(bu, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getTvLatestEpisodes(bu: Bu, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getTvLatestEpisodes(bu, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getTrendingMedias(bu: Bu, type: IlMediaType, onlyEpisodes: Boolean = false, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getTrendingMedias(bu, type, onlyEpisodes, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getLatestMediaByChannelId(bu: Bu, type: IlMediaType, channelId: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getLatestMediaByChannelId(bu, type, channelId, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getTvEpisodesByDate(bu: Bu, date: IlDate, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getTvEpisodesByDate(bu, date, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getEpisodeCompositionByUrn(showUrn: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Episode>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getEpisodeCompositionByUrn(showUrn, it.toIlPaging()) },
            nextCall = { ilService.getEpisodeCompositionByUrn(it) }
        )
    }

    fun getLiveCenterVideos(
        bu: Bu,
        type: LiveCenterType,
        onlyEventsWithResult: Boolean = true,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getLiveCenterVideos(bu, type, onlyEventsWithResult, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getScheduledLiveStreamVideos(
        bu: Bu,
        signLanguageOnly: Boolean = false,
        eventType: EventType,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = {
                ilService.getScheduledLiveStreamVideos(bu, signLanguageOnly, IlEventType(eventType), it.toIlPaging())
            },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getRadioEpisodesByDateByChannelId(bu: Bu, date: IlDate, channelId: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getRadioEpisodesByDateByChannelId(bu, date, channelId, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getRadioMostClickedMediasByChannelId(
        bu: Bu,
        channelId: String,
        onlyEpisodes: Boolean? = null,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,

            initialCall = { ilService.getRadioMostClickedMediasByChannelId(bu, channelId, onlyEpisodes, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getRadioMostClickedMediasByChannelId(bu: Bu, onlyEpisodes: Boolean? = null, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getRadioMostClickedMedias(bu, onlyEpisodes, it.toIlPaging()) },
            nextCall = { ilService.getMediaListNextUrl(it) }
        )
    }

    fun getRadioSongListByChannelId(bu: Bu, channelId: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Song>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getRadioSongListByChannelId(bu = bu, channelId = channelId, pageSize = it.toIlPaging()) },
            nextCall = { ilService.getSongListNextUrl(it) }
        )
    }

    fun getAllAlphabeticalShows(bu: Bu, transmission: Transmission, radioChannelId: String? = null): Flow<PagingData<Show>> {
        return createNextUrlPagingData(
            pageSize = DEFAULT_PAGE_SIZE,
            initialCall = {
                ilService.getAllAlphabeticalShows(
                    bu = bu, transmission = IlTransmission(transmission),
                    radioChannelId = radioChannelId
                )
            }, nextCall = { ilService.getShowListNextUrl(it) }
        )
    }

    fun getTvAlphabeticalShows(bu: Bu, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Show>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = { ilService.getTvAlphabeticalShows(bu = bu, pageSize = it.toIlPaging()) },
            nextCall = { ilService.getShowListNextUrl(it) }
        )
    }

    fun getRadioAlphabeticalShowsByChannelId(bu: Bu, radioChannelId: String, pageSize: Int = DEFAULT_PAGE_SIZE): Flow<PagingData<Show>> {
        return createNextUrlPagingData(
            pageSize = pageSize,
            initialCall = {
                ilService.getRadioAlphabeticalShowsByChannelId(bu = bu, channelId = radioChannelId, pageSize = it.toIlPaging())
            },
            nextCall = { ilService.getShowListNextUrl(it) }
        )
    }

    /**
     * Search media.
     *
     * @param searchTerm      search term (can be empty)
     * @param queryParameters list of query parameters to send to the server.
     * @param lastResult First server response (with aggregations and suggestions)
     *
     * Note that SWI does not support any query parameters (May 2019)
     */
    fun searchMedia(
        bu: Bu,
        searchTerm: String,
        queryParameters: SearchParams.MediaParams,
        lastResult: MutableSharedFlow<SearchResultWithMediaList>? = null,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Media>> {
        return createNextUrlPagingData(
            pageSize,
            initialCall = {
                val result = searchProvider.searchMedias(bu, searchTerm, queryParameters)
                lastResult?.emit(result)
                result
            },
            nextCall = {
                searchProvider.searchMediaWithNextUrl(it)
            }
        )
    }

    /**
     * Search show.
     *
     * @param searchTerm      search term
     * @param queryParameters list of query parameters to send to the server.
     * @param lastResult First server response (with total)
     * @return PagingDataSource with paginated results.
     */
    fun searchShow(
        bu: Bu,
        searchTerm: String,
        queryParameters: SearchParams.ShowParams,
        lastResult: MutableSharedFlow<SearchResultWithShowList>? = null,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Show>> {
        return createNextUrlPagingData(
            pageSize,
            initialCall = {
                val result = searchProvider.searchShows(bu, searchTerm, IlMediaType(queryParameters.mediaType ?: MediaType.VIDEO))
                lastResult?.emit(result)
                result
            },
            nextCall = {
                searchProvider.searchShowWithNextUrl(it)
            }
        )
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10

        private fun Int.toPagingConfig() = PagingConfig(pageSize = this, prefetchDistance = 1)

        private fun <T : Any> createNextUrlPagingData(
            pageSize: Int,
            initialCall: suspend (pageSize: Int) -> ListResult<T>?,
            nextCall: suspend (next: String) -> ListResult<T>?
        ): Flow<PagingData<T>> = Pager(config = pageSize.toPagingConfig(), pagingSourceFactory = {
            NextUrlPagingSource(
                initialCall = initialCall, nextCall = nextCall
            )
        }).flow
    }
}
