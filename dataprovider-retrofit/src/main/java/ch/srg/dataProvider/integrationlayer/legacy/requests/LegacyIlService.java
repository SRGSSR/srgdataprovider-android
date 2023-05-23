package ch.srg.dataProvider.integrationlayer.legacy.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.Map;

import ch.srg.dataProvider.integrationlayer.data.Channel;
import ch.srg.dataProvider.integrationlayer.data.EpisodeCompositionListResult;
import ch.srg.dataProvider.integrationlayer.data.Media;
import ch.srg.dataProvider.integrationlayer.data.MediaListResult;
import ch.srg.dataProvider.integrationlayer.data.MediaListWithShowResult;
import ch.srg.dataProvider.integrationlayer.data.MediaStatisticPost;
import ch.srg.dataProvider.integrationlayer.data.NowAndNext;
import ch.srg.dataProvider.integrationlayer.data.ProgramCompositionListResult;
import ch.srg.dataProvider.integrationlayer.data.ProgramGuide;
import ch.srg.dataProvider.integrationlayer.data.Show;
import ch.srg.dataProvider.integrationlayer.data.ShowListResult;
import ch.srg.dataProvider.integrationlayer.data.SongListResult;
import ch.srg.dataProvider.integrationlayer.data.TopicListResult;
import ch.srg.dataProvider.integrationlayer.data.content.Page;
import ch.srg.dataProvider.integrationlayer.data.content.Section;
import ch.srg.dataProvider.integrationlayer.data.search.SearchResultMediaList;
import ch.srg.dataProvider.integrationlayer.data.search.SearchResultShowList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public interface LegacyIlService {

    //region byUrns
    @GET("2.0/episodeComposition/latestByShow/byUrn/{urn}")
    Call<EpisodeCompositionListResult> getEpisodeComposition(@Path("urn") String urn, @Query("pageSize") Integer pageSize);

    @GET("2.0/episodeComposition/latestByShow/byUrn/{urn}")
    LiveData<IlResponse<EpisodeCompositionListResult>> getEpisodeCompositionLiveData(@Path("urn") String urn, @Query("pageSize") Integer pageSize);

    @GET("2.0/media/byUrn/{urn}")
    Call<Media> getMedia(@Path("urn") String urn);

    @GET("2.0/media/byUrn/{urn}")
    LiveData<IlResponse<Media>> getMediaLiveData(@Path("urn") String urn);

    @GET("2.0/show/byUrn/{urn}")
    Call<Show> getShow(@Path("urn") String urn);

    @GET("2.0/show/byUrn/{urn}")
    LiveData<IlResponse<Show>> getShowLiveData(@Path("urn") String urn);

    /**
     * @param channelUrn channel urn not the id.
     * @return Channel
     */
    @GET("2.0/channel/byUrn/{channelUrn}")
    Call<Channel> getChannel(@Path("channelUrn") String channelUrn);

    /**
     * @param channelUrn channel urn not the id.
     * @return Channel with the now and next program if available
     */
    @GET("2.0/channel/byUrn/{channelUrn}")
    LiveData<IlResponse<Channel>> getChannelLiveData(@Path("channelUrn") String channelUrn);

    /**
     * @param channelUrn    channel urn not the id.
     * @param liveStreamUrn stream urn useful for multi live stream channel
     * @return Channel with the now and next program if available
     */
    @GET("2.0/channel/byUrn/{channelUrn}/nowAndNext")
    Call<NowAndNext> getNowAndNext(@Path("channelUrn") String channelUrn, @Query("livestreamUrn") @Nullable String liveStreamUrn);

    /**
     * @param channelUrn    channel urn not the id.
     * @param liveStreamUrn stream urn useful for multi live stream channel
     * @return Channel with the now and next program if available
     */
    @GET("2.0/channel/byUrn/{channelUrn}/nowAndNext")
    LiveData<IlResponse<NowAndNext>> getNowAndNextLiveData(@Path("channelUrn") String channelUrn, @Query("livestreamUrn") @Nullable String liveStreamUrn);

    @GET("2.0/mediaList/byUrns")
    Call<MediaListResult> getMediaListFromUrns(@Query("urns") String urns);

    @GET("2.0/mediaList/byUrns")
    LiveData<IlResponse<MediaListResult>> getMediaListFromUrnsLiveData(@Query("urns") String urns);

    @GET("2.0/showList/byUrns")
    Call<ShowListResult> getShowListFromUrns(@Query("urns") String tabUrns);

    @GET("2.0/showList/byUrns")
    LiveData<IlResponse<ShowListResult>> getShowListFromUrnsLiveData(@Query("urns") String tabUrns);

    @GET("2.0/mediaList/latest/byTopicUrn/{urn}")
    Call<MediaListResult> getLatestTopic(@Path("urn") String topicUrn, @Query("pageSize") Integer pageSize);

    @GET("2.0/mediaList/latest/byTopicUrn/{urn}")
    LiveData<IlResponse<MediaListResult>> getLatestTopicLiveData(@Path("urn") String topicUrn, @Query("pageSize") Integer pageSize);

    @GET("2.0/mediaList/mostClicked/byTopicUrn/{topicUrn}")
    Call<MediaListResult> getMostClickedByTopic(@Path("topicUrn") String topicUrn, @Query("pageSize") Integer pageSize);

    @GET("2.0/mediaList/mostClicked/byTopicUrn/{topicUrn}")
    LiveData<IlResponse<MediaListResult>> getMostClickedByTopicLiveData(@Path("topicUrn") String topicUrn, @Query("pageSize") Integer pageSize);

    @GET("2.0/mediaList/latestByModuleConfigUrn/{urn}")
    Call<MediaListResult> getLatestByModuleConfig(@Path("urn") String moduleConfigUrn, @Query("pageSize") Integer pageSize);

    @GET("2.0/mediaList/latestByModuleConfigUrn/{urn}")
    LiveData<IlResponse<MediaListResult>> getLatestByModuleConfigLiveData(@Path("urn") String moduleConfigUrn, @Query("pageSize") Integer pageSize);

    @GET("2.0/mediaList/recommended/byUrn/{urn}")
    Call<MediaListResult> getRecommended(@Path("urn") String urn, @Query("pageSize") Integer pageSize);

    @GET("2.0/mediaList/recommended/byUrn/{urn}")
    LiveData<IlResponse<MediaListResult>> getRecommendedLiveData(@Path("urn") String urn, @Query("pageSize") Integer pageSize);

    /**
     * @param showUrns         Comma separated list of urns, max element = 15
     * @param onlyEpisodes     Return only episodes.
     * @param excludeEpisodes  Filter episodes.
     * @param maxPublishedDate Content is included from now to past until maxPublishedDate. Format: yyyy-MM or yyyy-MM-dd or ISO-8601 based format.
     * @param minPublishedDate Format: yyyy-MM or yyyy-MM-dd or ISO-8601 based format.
     * @param types            Comma separated list of types. Valid values are 'episode’, ‘segment’ and 'clip’.
     * @param pageSize         by default 10
     */
    @GET("2.0/mediaList/latest/byShowUrns")
    Call<MediaListResult> getLatestByShows(@NonNull @Query("urns") String showUrns,
                                           @Query("onlyEpisodes") Boolean onlyEpisodes,
                                           @Query("excludingEpisodes") Boolean excludeEpisodes,
                                           @Query("maxPublishedDate") String maxPublishedDate,
                                           @Query("minPublishedDate") String minPublishedDate,
                                           @Query("types") String types,
                                           @Query("pageSize") Integer pageSize);

    /**
     * @param showUrns         Comma separated list of urns, max element = 15
     * @param onlyEpisodes     Return only episodes.
     * @param excludeEpisodes  Filter episodes.
     * @param maxPublishedDate Content is included from now to past until maxPublishedDate. Format: yyyy-MM or yyyy-MM-dd or ISO-8601 based format.
     * @param minPublishedDate Format: yyyy-MM or yyyy-MM-dd or ISO-8601 based format.
     * @param types            Comma separated list of types. Valid values are 'episode’, ‘segment’ and 'clip’.
     * @param pageSize         by default 10
     */
    @GET("2.0/mediaList/latest/byShowUrns")
    LiveData<IlResponse<MediaListResult>> getLatestByShowsLiveData(@NonNull @Query("urns") String showUrns,
                                                                   @Query("onlyEpisodes") Boolean onlyEpisodes,
                                                                   @Query("excludingEpisodes") Boolean excludeEpisodes,
                                                                   @Query("maxPublishedDate") String maxPublishedDate,
                                                                   @Query("minPublishedDate") String minPublishedDate,
                                                                   @Query("types") String types,
                                                                   @Query("pageSize") Integer pageSize);
    //endregion byUrns

    //region tv
    @GET("2.0/{vendorName}/mediaList/video/livestreams")
    Call<MediaListResult> getVideoLivestreams(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/mediaList/video/livestreams")
    LiveData<IlResponse<MediaListResult>> getVideoLivestreamsLiveData(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/mediaList/video/scheduledLivestreams/livecenter")
    Call<MediaListResult> getVideoSportScheduledLiveStream(@Path("vendorName") String vendorName,
                                                           @Query("types") String type,
                                                           @Query("onlyEventsWithResult") boolean onlyEventsWithResult,
                                                           @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/scheduledLivestreams/livecenter")
    LiveData<IlResponse<MediaListResult>> getVideoSportScheduledLiveStreamLiveData(@Path("vendorName") String vendorName,
                                                                                   @Query("types") String type,
                                                                                   @Query("onlyEventsWithResult") boolean onlyEventsWithResult,
                                                                                   @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/scheduledLivestreams")
    Call<MediaListResult> getVideoScheduledLiveStream(@Path("vendorName") String vendorName,
                                                      @Query("signLanguageOnly") boolean signLanguageOnly,
                                                      @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/scheduledLivestreams")
    LiveData<IlResponse<MediaListResult>> getVideoScheduledLiveStreamLiveData(@Path("vendorName") String vendorName,
                                                                              @Query("signLanguageOnly") boolean signLanguageOnly,
                                                                              @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/trending")
    Call<MediaListResult> getVideoTrendingAndEditorial(@Path("vendorName") String vendorName,
                                                       @Query("pageSize") Integer pageSize,
                                                       @Query("onlyEpisodes") boolean onlyEpisodes);

    @GET("2.0/{vendorName}/mediaList/video/trending")
    LiveData<IlResponse<MediaListResult>> getVideoTrendingAndEditorialLiveData(@Path("vendorName") String vendorName,
                                                                               @Query("pageSize") Integer pageSize,
                                                                               @Query("onlyEpisodes") boolean onlyEpisodes);

    @GET("2.0/{vendorName}/topicList/tv")
    Call<TopicListResult> getTopics(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/topicList/tv")
    LiveData<IlResponse<TopicListResult>> getTopicsLiveData(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/mediaList/video/mostClicked")
    Call<MediaListResult> getMostClickedVideo(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/mostClicked")
    LiveData<IlResponse<MediaListResult>> getMostClickedVideoLiveData(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/latestEpisodes")
    Call<MediaListResult> getLatestEpisodes(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/latestEpisodes")
    LiveData<IlResponse<MediaListResult>> getLatestEpisodesLiveData(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/soonExpiring")
    Call<MediaListResult> getSoonExpiringVideo(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/soonExpiring")
    LiveData<IlResponse<MediaListResult>> getSoonExpiringVideoLiveData(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/soonExpiring")
    Call<MediaListResult> getSoonExpiringVideoForTopic(@Path("vendorName") String vendorName, @Query("topicId") String topicId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/soonExpiring")
    LiveData<IlResponse<MediaListResult>> getSoonExpiringVideoForTopicLiveData(@Path("vendorName") String vendorName, @Query("topicId") String topicId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/showList/tv/alphabetical")
    Call<ShowListResult> getAlphabeticalTvShowList(@Path("vendorName") String vendorName, @Query("pageSize") String pageSize);

    @GET("2.0/{vendorName}/showList/tv/alphabetical")
    LiveData<IlResponse<ShowListResult>> getAlphabeticalTvShowListLiveData(@Path("vendorName") String vendorName, @Query("pageSize") String pageSize);

    @GET("2.0/{vendorName}/mediaList/video/episodesByDate/{date}")
    Call<MediaListResult> getVideoByDate(@Path("vendorName") String vendorName, @Path("date") String date, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/episodesByDate/{date}")
    LiveData<IlResponse<MediaListResult>> getVideoByDateLiveData(@Path("vendorName") String vendorName, @Path("date") String date, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/latestEpisodes/webFirst")
    Call<MediaListResult> getVideoWebFirst(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/latestEpisodes/webFirst")
    LiveData<IlResponse<MediaListResult>> getVideoWebFirstLiveData(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/heroStage")
    Call<MediaListResult> getVideoHeroStage(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/mediaList/video/heroStage")
    LiveData<IlResponse<MediaListResult>> getVideoHeroStageLiveData(@Path("vendorName") String vendorName);
    //endregion tv

    //region radio
    @GET("2.0/{vendorName}/mediaList/audio/livestreams")
    Call<MediaListResult> getAudioLivestreams(@Path("vendorName") String vendorName, @Query("includeThirdPartyStreams") boolean includeThirdPartyStreams);

    @GET("2.0/{vendorName}/mediaList/audio/livestreams")
    LiveData<IlResponse<MediaListResult>> getAudioLivestreamsLiveData(@Path("vendorName") String vendorName, @Query("includeThirdPartyStreams") boolean includeThirdPartyStreams);

    @GET("2.0/{vendorName}/mediaList/audio/livestreams?onlyThirdPartyContentProvider=ssatr")
    Call<MediaListResult> getAudioLivestreamThirdPartyContent(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/mediaList/audio/livestreams?onlyThirdPartyContentProvider=ssatr")
    LiveData<IlResponse<MediaListResult>> getAudioLivestreamThirdPartyContentLiveData(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/mediaList/audio/livestreamsByChannel/{channel}")
    Call<MediaListResult> getAudioLivestreamsByChannel(@Path("vendorName") String vendorName, @Path("channel") String channelId);

    @GET("2.0/{vendorName}/mediaList/audio/livestreamsByChannel/{channel}")
    LiveData<IlResponse<MediaListResult>> getAudioLivestreamsByChannelLiveData(@Path("vendorName") String vendorName, @Path("channel") String channelId);

    @GET("2.0/{vendorName}/mediaList/audio/trending")
    Call<MediaListResult> getAudioEditorial(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize, @Query("onlyEpisodes") boolean onlyEpisodes);

    @GET("2.0/{vendorName}/mediaList/audio/trending")
    LiveData<IlResponse<MediaListResult>> getAudioEditorialLiveData(@Path("vendorName") String vendorName, @Query("pageSize") Integer pageSize, @Query("onlyEpisodes") boolean onlyEpisodes);

    @GET("2.0/{vendorName}/mediaList/audio/mostClickedByChannel/{channel}")
    Call<MediaListResult> getMostClickedAudioForChannel(@Path("vendorName") String vendorName, @Path("channel") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/mostClickedByChannel/{channel}")
    LiveData<IlResponse<MediaListResult>> getMostClickedAudioForChannelLiveData(@Path("vendorName") String vendorName, @Path("channel") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/mostClicked")
    Call<MediaListResult> getMostClickedAudio(@Path("vendorName") String vendorName, @Query("onlyEpisodes") boolean onlyEpisodes, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/mostClicked")
    LiveData<IlResponse<MediaListResult>> getMostClickedAudioLiveData(@Path("vendorName") String vendorName, @Query("onlyEpisodes") boolean onlyEpisodes, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/latestByChannel/{channel}")
    Call<MediaListResult> getLatestAudioForChannel(@Path("vendorName") String vendorName, @Path("channel") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/latestByChannel/{channel}")
    LiveData<IlResponse<MediaListResult>> getLatestAudioForChannelLiveData(@Path("vendorName") String vendorName, @Path("channel") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/latestEpisodesByChannel/{channel}")
    Call<MediaListResult> getLatestAudioEpisodesForChannel(@Path("vendorName") String vendorName, @Path("channel") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/latestEpisodesByChannel/{channel}")
    LiveData<IlResponse<MediaListResult>> getLatestAudioEpisodesForChannelLiveData(@Path("vendorName") String vendorName, @Path("channel") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/latestByChannel/{channel}")
    Call<MediaListResult> getVideoLatestByChannel(@Path("vendorName") String vendorName, @Path("channel") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/video/latestByChannel/{channel}")
    LiveData<IlResponse<MediaListResult>> getVideoLatestByChannelLiveData(@Path("vendorName") String vendorName, @Path("channel") String channelId,
                                                                          @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/showList/radio/alphabeticalByChannel/{channelId}")
    Call<ShowListResult> getAlphabeticalRadioShowList(@Path("vendorName") String vendorName, @Path("channelId") String channelId,
                                                      @Query("pageSize") String pageSize);

    @GET("2.0/{vendorName}/showList/radio/alphabeticalByChannel/{channelId}")
    LiveData<IlResponse<ShowListResult>> getAlphabeticalRadioShowListLiveData(@Path("vendorName") String vendorName, @Path("channelId") String channelId,
                                                                              @Query("pageSize") String pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/episodesByDateAndChannel/{date}/{channelId}")
    Call<MediaListResult> getAudioByDate(@Path("vendorName") String vendorName, @Path("date") String date,
                                         @Path("channelId") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/mediaList/audio/episodesByDateAndChannel/{date}/{channelId}")
    LiveData<IlResponse<MediaListResult>> getAudioByDateLiveData(@Path("vendorName") String vendorName, @Path("date") String date,
                                                                 @Path("channelId") String channelId, @Query("pageSize") Integer pageSize);

    @GET("2.0/{vendorName}/songList/radio/byChannel/{channelId}")
    Call<SongListResult> getSongList(@Path("vendorName") String vendorName, @Path("channelId") String channelId, @Query("pageSize") String pageSize);

    @GET("2.0/{vendorName}/songList/radio/byChannel/{channelId}")
    LiveData<IlResponse<SongListResult>> getSongListLiveData(@Path("vendorName") String vendorName, @Path("channelId") String channelId, @Query("pageSize") String pageSize);

    //endregion radio

    //region search
    @GET("2.0/{vendorName}/showList/mostClickedSearchResults")
    Call<ShowListResult> getMostClickedSearchedShow(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/showList/mostClickedSearchResults")
    LiveData<IlResponse<ShowListResult>> getMostClickedSearchedShowLiveData(@Path("vendorName") String vendorName);

    @GET("2.0/{vendorName}/searchResultMediaList")
    Call<SearchResultMediaList> searchMedia(@Path("vendorName") String vendorName, @Query("q") String searchTerm, @QueryMap Map<String, String> params);

    @GET("2.0/{vendorName}/searchResultMediaList")
    LiveData<IlResponse<SearchResultMediaList>> searchMediaLiveData(@Path("vendorName") String vendorName, @Query("q") String searchTerm, @QueryMap Map<String, String> params);

    @GET("2.0/{vendorName}/searchResultShowList")
    Call<SearchResultShowList> searchShow(@Path("vendorName") String vendorName, @Query("q") String searchTerm, @QueryMap Map<String, String> params);

    @GET("2.0/{vendorName}/searchResultShowList")
    LiveData<IlResponse<SearchResultShowList>> searchShowLiveData(@Path("vendorName") String vendorName, @Query("q") String searchTerm, @QueryMap Map<String, String> params);
    //endregion search

    //region buSpecific

    /**
     * @param vendorName       vendor name in lower case (srf,rts,rsi,rts)
     * @param transmissionType transmission type in lower case
     * @param channelId        Channel id not the urn
     * @param minEndTime       Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param maxStartTime     Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param liveStreamId     id of a regional journal live stream id, not urn!
     */
    @GET("2.0/{vendorName}/programListComposition/{transmissionType}/byChannel/{channelId}")
    Call<ProgramCompositionListResult> programList(@Path("vendorName") String vendorName,
                                                   @Path("transmissionType") String transmissionType,
                                                   @Path("channelId") String channelId,
                                                   @Query("pageSize") Integer pageSize,
                                                   @Query("minEndTime") @Nullable String minEndTime,
                                                   @Query("maxStartTime") @Nullable String maxStartTime,
                                                   @Query("livestreamId") @Nullable String liveStreamId);

    /**
     * @param vendorName       vendor name in lower case (srf,rts,rsi,rts)
     * @param transmissionType transmission type in lower case
     * @param channelId        Channel id not the urn
     * @param minEndTime       Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param maxStartTime     Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param liveStreamId     id of a regional journal live stream id, not urn!
     */
    @GET("2.0/{vendorName}/programListComposition/{transmissionType}/byChannel/{channelId}")
    LiveData<IlResponse<ProgramCompositionListResult>> programListLiveData(@Path("vendorName") String vendorName,
                                                                           @Path("transmissionType") String transmissionType,
                                                                           @Path("channelId") String channelId,
                                                                           @Query("pageSize") Integer pageSize,
                                                                           @Query("minEndTime") @Nullable String minEndTime,
                                                                           @Query("maxStartTime") @Nullable String maxStartTime,
                                                                           @Query("livestreamId") @Nullable String liveStreamId);

    /**
     * @param vendorName vendor name in lower case (srf,rts,rsi,rts)
     * @param date       A ISO-8601 date (YYYY-MM-DD).
     * @param reduced    When set to true, only basic program information is returned (Default value: false).
     */
    @GET("2.0/{vendorName}/programGuide/tv/byDate/{date}")
    Call<ProgramGuide> programGuide(@Path("vendorName") String vendorName,
                                    @Path("date") String date,
                                    @Query("reduced") Boolean reduced);

    /**
     * @param vendorName vendor name in lower case (srf,rts,rsi,rts)
     * @param date       A ISO-8601 date (YYYY-MM-DD).
     * @param reduced    When set to true, only basic program information is returned (Default value: false).
     */
    @GET("2.0/{vendorName}/programGuide/tv/byDate/{date}")
    LiveData<IlResponse<ProgramGuide>> programGuideLiveData(@Path("vendorName") String vendorName,
                                                            @Path("date") String date,
                                                            @Query("reduced") Boolean reduced);

    /**
     * @param vendorName vendor name in lower case (srf,rts,rsi,rts)
     * @param date       A ISO-8601 date (YYYY-MM-DD).
     * @param reduced    When set to true, only basic program information is returned (Default value: false).
     */
    @GET("2.0/{vendorName}/programGuideNonSrg/tv/byDate/{date}")
    Call<ProgramGuide> programGuideNonSrg(@Path("vendorName") String vendorName,
                                          @Path("date") String date,
                                          @Query("reduced") Boolean reduced);

    /**
     * @param vendorName vendor name in lower case (srf,rts,rsi,rts)
     * @param date       A ISO-8601 date (YYYY-MM-DD).
     * @param reduced    When set to true, only basic program information is returned (Default value: false).
     */
    @GET("2.0/{vendorName}/programGuideNonSrg/tv/byDate/{date}")
    LiveData<IlResponse<ProgramGuide>> programGuideNonSrgLiveData(@Path("vendorName") String vendorName,
                                                                  @Path("date") String date,
                                                                  @Query("reduced") Boolean reduced);


    //endregion buSpecific

    //region pac
    @GET("2.0/{vendorName}/page/landingPage/byProduct/{product}")
    Call<Page> getLandingPageByProduct(@Path("vendorName") String vendorName, @Path("product") String product, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/page/landingPage/byProduct/{product}")
    LiveData<IlResponse<Page>> getLandingPageByProductLiveData(@Path("vendorName") String vendorName, @Path("product") String product, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/page/{pageId}")
    Call<Page> getPage(@Path("vendorName") String vendorName, @Path("pageId") String pageId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/page/{pageId}")
    LiveData<IlResponse<Page>> getPageLiveData(@Path("vendorName") String vendorName, @Path("pageId") String pageId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/page/byTopicUrn/{topicUrn}")
    Call<Page> getPageForTopic(@Path("vendorName") String vendorName, @Path("topicUrn") String topicUrn, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/page/byTopicUrn/{topicUrn}")
    LiveData<IlResponse<Page>> getPageForTopicLiveData(@Path("vendorName") String vendorName, @Path("topicUrn") String topicUrn, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/{sectionId}")
    Call<Section> getSection(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/{sectionId}")
    LiveData<IlResponse<Section>> getSectionLiveData(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/mediaSection/{sectionId}")
    Call<MediaListResult> getMediaListForMediaSection(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/mediaSection/{sectionId}")
    LiveData<IlResponse<MediaListResult>> getMediaListForMediaSectionLiveData(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/mediaSectionWithShow/{sectionId}")
    Call<MediaListWithShowResult> getMediaListForMediaSectionWithShow(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/mediaSectionWithShow/{sectionId}")
    LiveData<IlResponse<MediaListWithShowResult>> getMediaListForMediaSectionWithShowLiveData(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/showSection/{sectionId}")
    Call<ShowListResult> getShowListForShowSection(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);

    @GET("2.0/{vendorName}/section/showSection/{sectionId}")
    LiveData<IlResponse<ShowListResult>> getShowListForShowSectionLiveData(@Path("vendorName") String vendorName, @Path("sectionId") String sectionId, @Query("isPublished") Boolean isPublished);
    //endregion pac

    //region statistics
    @POST("2.0/mediaStatistic/byUrn/{urn}/clicked")
    Call<Object> postMediaClicked(@Path("urn") String urn, @Body MediaStatisticPost mediaStatisticsBody);

    @POST("2.0/mediaStatistic/byUrn/{urn}/liked")
    Call<Object> postMediaLiked(@Path("urn") String urn, @Body MediaStatisticPost mediaStatisticsBody);

    //endregion statistics

    //region nextUrls
    @GET
    Call<MediaListResult> getMediaListNextUrl(@Url String url);

    @GET
    LiveData<IlResponse<MediaListResult>> getMediaListNextUrlLiveData(@Url String url);

    @GET
    Call<EpisodeCompositionListResult> getEpisodeCompositionNextUrl(@Url String url);

    @GET
    LiveData<IlResponse<EpisodeCompositionListResult>> getEpisodeCompositionNextUrlLiveData(@Url String url);

    @GET
    Call<ShowListResult> getShowListNextUrl(@Url String url);

    @GET
    LiveData<IlResponse<ShowListResult>> getShowListNextUrlLiveData(@Url String url);

    @GET
    Call<SongListResult> getSongListNextUrl(@Url String url);

    @GET
    LiveData<IlResponse<SongListResult>> getSongListNextUrlLiveData(@Url String url);

    @GET
    Call<SearchResultMediaList> searchMediaNextUrl(@Url String url);

    @GET
    LiveData<IlResponse<SearchResultMediaList>> searchMediaNextUrlLiveData(@Url String url);

    @GET
    Call<SearchResultShowList> searchShowNextUrl(@Url String url);

    @GET
    LiveData<IlResponse<SearchResultShowList>> searchShowNextUrlLiveData(@Url String url);
    //endregion nextUrls
}
