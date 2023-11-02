package ch.srg.dataProvider.integrationlayer.request

import ch.srg.dataProvider.integrationlayer.data.remote.Channel
import ch.srg.dataProvider.integrationlayer.data.remote.ChannelListResult
import ch.srg.dataProvider.integrationlayer.data.remote.EpisodeCompositionListResult
import ch.srg.dataProvider.integrationlayer.data.remote.LiveCenterType
import ch.srg.dataProvider.integrationlayer.data.remote.Media
import ch.srg.dataProvider.integrationlayer.data.remote.MediaComposition
import ch.srg.dataProvider.integrationlayer.data.remote.MediaListResult
import ch.srg.dataProvider.integrationlayer.data.remote.MediaListWithShowResult
import ch.srg.dataProvider.integrationlayer.data.remote.NowAndNext
import ch.srg.dataProvider.integrationlayer.data.remote.Page
import ch.srg.dataProvider.integrationlayer.data.remote.ProgramCompositionListResult
import ch.srg.dataProvider.integrationlayer.data.remote.ProgramGuide
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultMediaList
import ch.srg.dataProvider.integrationlayer.data.remote.SearchResultShowList
import ch.srg.dataProvider.integrationlayer.data.remote.Section
import ch.srg.dataProvider.integrationlayer.data.remote.Show
import ch.srg.dataProvider.integrationlayer.data.remote.ShowListResult
import ch.srg.dataProvider.integrationlayer.data.remote.SongListResult
import ch.srg.dataProvider.integrationlayer.data.remote.TopicListResult
import ch.srg.dataProvider.integrationlayer.request.parameters.Bu
import ch.srg.dataProvider.integrationlayer.request.parameters.IlDate
import ch.srg.dataProvider.integrationlayer.request.parameters.IlDateTime
import ch.srg.dataProvider.integrationlayer.request.parameters.IlMediaType
import ch.srg.dataProvider.integrationlayer.request.parameters.IlPaging
import ch.srg.dataProvider.integrationlayer.request.parameters.IlTransmission
import ch.srg.dataProvider.integrationlayer.request.parameters.IlUrns
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * Il Retrofit service
 *
 * Retrofit suspend function already called in IO Dispatcher ie withContext(Dispatchers.IO)
 * So no need to call it when calling it inside a Coroutine scope.
 *
 * suspend function may throws those exceptions :
 *  - retrofit2.HttpException
 *  - java.io.IOException
 *
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
interface IlService {

    //region urn

    @GET("2.1/mediaComposition/byUrn/{urn}")
    suspend fun getMediaComposition(
        @Path("urn") urn: String,
        @Query("onlyChapters") onlyChapters: Boolean? = null
    ): MediaComposition

    @GET("2.0/media/byUrn/{urn}")
    suspend fun getMediaByUrn(@Path("urn") urn: String): Media

    @GET("2.0/mediaList/byUrns")
    suspend fun getMediaListFromUrns(@Query("urns") urns: IlUrns): MediaListResult

    @GET("2.0/mediaList/latest/byTopicUrn/{urn}")
    suspend fun getLatestMediaByTopicUrn(
        @Path("urn") topicUrn: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/mediaList/mostClicked/byTopicUrn/{topicUrn}")
    suspend fun getMostClickedMediaByTopicUrn(
        @Path("topicUrn") topicUrn: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/mediaList/recommended/byUrn/{urn}")
    suspend fun getMediaRecommendedByUrn(
        @Path("urn") urn: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    /**
     * @param showUrns         List of show urns, max element = 15
     * @param onlyEpisodes     Return only episodes.
     * @param excludeEpisodes  Filter episodes.
     * @param maxPublishedDate Content is included from now to past until maxPublishedDate.
     * @param minPublishedDate Content from minPublishedDate
     * @param types            Comma separated list of types. Valid values are 'episode’, ‘segment’ and 'clip’.
     * @param pageSize         by default 10
     */
    @GET("2.0/mediaList/latest/byShowUrns")
    suspend fun getLatestMediaByShowUrns(
        @Query("urns") showUrns: IlUrns,
        @Query("onlyEpisodes") onlyEpisodes: Boolean? = null,
        @Query("excludingEpisodes") excludeEpisodes: Boolean? = null,
        @Query("maxPublishedDate") maxPublishedDate: IlDateTime? = null,
        @Query("minPublishedDate") minPublishedDate: IlDateTime? = null,
        @Query("types") types: String? = null,
        @Query("pageSize") pageSize: IlPaging? = null
    ): MediaListResult

    @GET("2.0/mediaList/latest/byShowUrn/{showUrn}")
    suspend fun getLatestMediaByShowUrn(
        @Path("showUrn") showUrn: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/episodeComposition/latestByShow/byUrn/{urn}")
    suspend fun getEpisodeCompositionByUrn(
        @Path("urn") urn: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): EpisodeCompositionListResult

    @GET("2.0/channel/byUrn/{channelUrn}")
    suspend fun getChannelByUrn(@Path("channelUrn") channelUrn: String): Channel

    /**
     * @param channelUrn    channel urn not the id.
     * @param liveStreamUrn stream urn useful for multi live stream channel
     * @return Channel with the now and next program if available
     */
    @GET("2.0/channel/byUrn/{channelUrn}/nowAndNext")
    suspend fun getNowAndNextByChannelUrn(
        @Path("channelUrn") channelUrn: String,
        @Query("livestreamUrn") liveStreamUrn: String? = null
    ): NowAndNext

    @GET("2.0/show/byUrn/{urn}")
    suspend fun getShowByUrn(@Path("urn") urn: String): Show

    @GET("2.0/showList/byUrns")
    suspend fun getShowListFromUrns(@Query("urns") tabUrns: IlUrns): ShowListResult

    //endregion urn

    //region tv

    @GET("2.0/{bu}/mediaList/video/livestreams")
    suspend fun getTvLiveStreams(@Path("bu") bu: Bu): MediaListResult

    /**
     * @param topicId topicId not Urn!
     * @return most clicked video for topicId if not null otherwise the soon expiring videos
     */
    @GET("2.0/{bu}/mediaList/video/mostClicked")
    suspend fun getTvMostClickedMedias(
        @Path("bu") bu: Bu,
        @Query("topicId") topicId: String? = null,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/showList/tv/mostClicked")
    suspend fun getTvMostClickedShows(
        @Path("bu") bu: Bu,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): ShowListResult

    @GET("2.0/{bu}/showList/tv/byTopic/{topicId}/mostClicked")
    suspend fun getTvMostClickedShowsByTopicId(
        @Path("bu") bu: Bu,
        @Path("topicId") topicId: String,
        @Query("onlyActiveShows") onlyActiveShows: Boolean = true,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): ShowListResult

    @GET("2.0/{bu}/mediaList/video/latestEpisodes")
    suspend fun getTvLatestEpisodes(
        @Path("bu") bu: Bu,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/video/latestEpisodes/webFirst")
    suspend fun getTvWebFirstMedias(
        @Path("bu") bu: Bu,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    /**
     * @param topicId topicId not Urn!
     * @return soon expiring video for topicId if not null otherwise the soon expiring videos
     */
    @GET("2.0/{bu}/mediaList/video/soonExpiring")
    suspend fun getTvSoonExpiringMedias(
        @Path("bu") bu: Bu,
        @Query("topicId") topicId: String? = null,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/video/heroStage")
    suspend fun getTvHeroStageMedias(@Path("bu") bu: Bu): MediaListResult

    /**
     * @param reduced    When set to true, only basic program information is returned (Default value: false).
     */
    @GET("2.0/{bu}/programGuide/tv/byDate/{date}")
    suspend fun getProgramGuide(
        @Path("bu") bu: Bu,
        @Path("date") date: IlDate,
        @Query("reduced") reduced: Boolean = false
    ): ProgramGuide

    @GET("2.0/{bu}/programGuideNonSrg/tv/byDate/{date}")
    suspend fun programGuideNonSrg(
        @Path("bu") bu: Bu,
        @Path("date") date: IlDate,
        @Query("reduced") reduced: Boolean = false
    ): ProgramGuide

    @GET("2.0/{bu}/showList/tv/alphabetical")
    suspend fun getTvAlphabeticalShows(
        @Path("bu") bu: Bu,
        @Query("pageSize") pageSize: IlPaging?
    ): ShowListResult

    //endregion tv

    //region radio

    /**
     * @param includeThirdPartyStreams Add livestreams of third party content provider.
     * @param onlyThirdPartyContentProvider only 'ssatr' is supported
     */
    @GET("2.0/{bu}/mediaList/audio/livestreams")
    suspend fun getRadioLiveStreams(
        @Path("bu") bu: Bu,
        @Query("includeThirdPartyStreams") includeThirdPartyStreams: Boolean? = null,
        @Query("onlyThirdPartyContentProvider") onlyThirdPartyContentProvider: String? = null
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/audio/livestreamsByChannel/{channelId}")
    suspend fun getRadioLiveStreamsByChannelId(
        @Path("bu") bu: Bu,
        @Path("channelId") channelId: String
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/audio/mostClicked")
    suspend fun getRadioMostClickedMedias(
        @Path("bu") bu: Bu,
        @Query("onlyEpisodes") onlyEpisodes: Boolean? = null,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/audio/mostClickedByChannel/{channelId}")
    suspend fun getRadioMostClickedMediasByChannelId(
        @Path("bu") bu: Bu,
        @Path("channelId") channelId: String,
        @Query("onlyEpisodes") onlyEpisodes: Boolean? = null,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/showList/radio/mostClicked")
    suspend fun getRadioMostClickedShows(
        @Path("bu") bu: Bu,
        @Query("channelId") channelId: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): ShowListResult

    /**
     * @param date A ISO-8601 date (YYYY-MM-DD)
     */
    @GET("2.0/{bu}/mediaList/audio/episodesByDateAndChannel/{date}/{channelId}")
    suspend fun getRadioEpisodesByDateByChannelId(
        @Path("bu") bu: Bu,
        @Path("date") date: IlDate?,
        @Path("channelId") channelId: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    /**
     * @param date A ISO-8601 date (YYYY-MM-DD)
     */
    @GET("2.0/{bu}/mediaList/video/episodesByDate/{date}")
    suspend fun getTvEpisodesByDate(
        @Path("bu") bu: Bu,
        @Path("date") date: IlDate,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/songList/radio/byChannel/{channelId}")
    suspend fun getRadioSongListByChannelId(
        @Path("bu") bu: Bu,
        @Path("channelId") channelId: String?,
        @Query("pageSize") pageSize: IlPaging? = null
    ): SongListResult

    @GET("2.0/{bu}/showList/radio/alphabeticalByChannel/{channelId}")
    suspend fun getRadioAlphabeticalShowsByChannelId(
        @Path("bu") bu: Bu,
        @Path("channelId") channelId: String,
        @Query("pageSize") pageSize: IlPaging?
    ): ShowListResult

    //endregion radio

    //region livecenter

    @GET("2.0/{bu}/mediaList/video/scheduledLivestreams/livecenter")
    suspend fun getLiveCenterVideos(
        @Path("bu") bu: Bu,
        @Query("types") type: LiveCenterType? = null,
        @Query("onlyEventsWithResult") onlyEventsWithResult: Boolean = true,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/video/scheduledLivestreams")
    suspend fun getScheduledLiveStreamVideos(
        @Path("bu") bu: Bu,
        @Query("signLanguageOnly") signLanguageOnly: Boolean = false,
        @Query("pageSize") pageSize: IlPaging.Size? = null,
    ): MediaListResult

    //endregion livecenter

    //region tv and radio

    @GET("2.0/{bu}/mediaList/{type}/trending")
    suspend fun getTrendingMedias(
        @Path("bu") bu: Bu,
        @Path("type") type: IlMediaType,
        @Query("onlyEpisodes") onlyEpisodes: Boolean? = null,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/{type}/latestEpisodesByChannel/{channelId}")
    suspend fun getLatestEpisodesByChannelId(
        @Path("bu") bu: Bu,
        @Path("type") type: IlMediaType,
        @Path("channelId") channelId: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null
    ): MediaListResult

    @GET("2.0/{bu}/mediaList/{type}/latestByChannel/{channelId}")
    suspend fun getLatestMediaByChannelId(
        @Path("bu") bu: Bu,
        @Path("type") type: IlMediaType,
        @Path("channelId") channelId: String,
        @Query("pageSize") pageSize: IlPaging.Size? = null,
    ): MediaListResult

    @GET("2.0/{bu}/channelList/{transmission}")
    suspend fun getChannelList(
        @Path("bu") bu: Bu,
        @Path("transmission") transmissionType: IlTransmission
    ): ChannelListResult

    @GET("2.0/{bu}/topicList/{transmission}")
    suspend fun getTopics(
        @Path("bu") bu: Bu,
        @Path("transmission") transmissionType: IlTransmission
    ): TopicListResult

    /**
     * @param channelId        Channel id not the urn
     * @param minEndTime       Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param maxStartTime     Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param liveStreamId     id of a regional journal live stream id, not urn!
     */
    @GET("2.0/{bu}/programListComposition/{transmissionType}/byChannel/{channelId}")
    suspend fun getProgramListByChannelId(
        @Path("bu") bu: Bu,
        @Path("transmissionType") transmissionType: IlTransmission,
        @Path("channelId") channelId: String,
        @Query("minEndTime") minEndTime: IlDateTime?,
        @Query("maxStartTime") maxStartTime: IlDateTime?,
        @Query("livestreamId") liveStreamId: String?,
        @Query("pageSize") pageSize: IlPaging.Size? = null,
    ): ProgramCompositionListResult

    @GET("2.0/{bu}/showList/{transmission}/alphabetical/all")
    suspend fun getAllAlphabeticalShows(
        @Path("bu") bu: Bu,
        @Path("transmission") transmission: IlTransmission,
        @Query("channelId") radioChannelId: String? = null
    ): ShowListResult

    @GET("2.0/{bu}/showList/mostClickedSearchResults")
    suspend fun getTop10MostClickedSearchShow(
        @Path("bu") bu: Bu
    ): ShowListResult

    @GET("2.0/{bu}/showList/{transmission}/mostClickedSearchResults")
    suspend fun getMostClickedSearchShow(
        @Path("bu") bu: Bu,
        @Path("transmission") transmission: IlTransmission,
    ): ShowListResult

    //endregion tv and radio

    //region search

    /**
     * <pre>SWI supports only the parameters ‘q’ (must not be empty), ‘pageSize’ and 'next’
     * For easier client integration the parameter ‘includeAggregations’ is also allowed for SWI, but not supported by the backend.
     *
     * RTS support all filter parameters except the ‘quality’ parameter.
     *
     * On the aggregations duration means
     * duration:0, count:2 -> Two medias with a duration up to 59999 milliseconds
     * duration:1, count:2 -> Two medias with a duration between 60000 and 119999 milliseconds</pre>
     */
    @GET("2.0/{bu}/searchResultMediaList")
    suspend fun getSearchMedias(
        @Path("bu") bu: Bu,
        @Query("q") searchTerm: String,
        @QueryMap params: Map<String, String>? = null,
        @Query("pageSize") pageSize: IlPaging.Size? = null,
    ): SearchResultMediaList

    @GET("2.0/{bu}/searchResultShowList")
    suspend fun getSearchShows(
        @Path("bu") bu: Bu,
        @Query("q") searchTerm: String,
        @Query("mediaType") mediaType: IlMediaType? = null,
        @Query("pageSize") pageSize: IlPaging? = null,
    ): SearchResultShowList

    //endregion search

    //region content

    @GET("2.0/{bu}/page/landingPage/byProduct/{product}")
    suspend fun getLandingPageByProduct(
        @Path("bu") bu: Bu,
        @Path("product") product: String,
        @Query("isPublished") isPublished: Boolean? = null
    ): Page

    @GET("2.0/{bu}/page/{pageId}")
    suspend fun getPage(
        @Path("bu") bu: Bu,
        @Path("pageId") pageId: String,
        @Query("isPublished") isPublished: Boolean? = null
    ): Page

    @GET("2.0/{bu}/page/byTopicUrn/{topicUrn}")
    suspend fun getPageForTopic(
        @Path("bu") bu: Bu,
        @Path("topicUrn") topicUrn: String,
        @Query("isPublished") isPublished: Boolean? = null
    ): Page

    @GET("2.0/{bu}/section/{sectionId}")
    suspend fun getSection(
        @Path("bu") bu: Bu,
        @Path("sectionId") sectionId: String,
        @Query("isPublished") isPublished: Boolean? = null
    ): Section

    @GET("2.0/{bu}/section/mediaSection/{sectionId}")
    suspend fun getMediaListForMediaSection(
        @Path("bu") bu: Bu,
        @Path("sectionId") sectionId: String,
        @Query("isPublished") isPublished: Boolean? = null
    ): MediaListResult

    @GET("2.0/{bu}/section/mediaSectionWithShow/{sectionId}")
    suspend fun getMediaListForMediaSectionWithShow(
        @Path("bu") bu: Bu,
        @Path("sectionId") sectionId: String,
        @Query("isPublished") isPublished: Boolean? = null
    ): MediaListWithShowResult

    @GET("2.0/{bu}/section/showSection/{sectionId}")
    suspend fun getShowListForShowSection(
        @Path("bu") bu: Bu,
        @Path("sectionId") sectionId: String,
        @Query("isPublished") isPublished: Boolean? = null
    ): ShowListResult
    //endregion content

    //region nextUrls

    @GET
    suspend fun getMediaListNextUrl(@Url url: String): MediaListResult

    @GET
    suspend fun getEpisodeCompositionNextUrl(@Url url: String): EpisodeCompositionListResult

    @GET
    suspend fun getShowListNextUrl(@Url url: String): ShowListResult

    @GET
    suspend fun getSongListNextUrl(@Url url: String): SongListResult

    @GET
    suspend fun getSearchMediaNextUrl(@Url url: String): SearchResultMediaList

    @GET
    suspend fun getSearchShowNextUrl(@Url url: String): SearchResultShowList
    //endregion nextUrls
}
