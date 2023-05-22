package ch.srg.dataProvider.integrationlayer.legacy.requests;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ch.srg.dataProvider.integrationlayer.data.Channel;
import ch.srg.dataProvider.integrationlayer.data.Chapter;
import ch.srg.dataProvider.integrationlayer.data.EpisodeCompositionListResult;
import ch.srg.dataProvider.integrationlayer.data.LiveCenterType;
import ch.srg.dataProvider.integrationlayer.data.Media;
import ch.srg.dataProvider.integrationlayer.data.MediaFilter;
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
import ch.srg.dataProvider.integrationlayer.data.Transmission;
import ch.srg.dataProvider.integrationlayer.data.Vendor;
import ch.srg.dataProvider.integrationlayer.data.content.Page;
import ch.srg.dataProvider.integrationlayer.data.content.Section;
import ch.srg.dataProvider.integrationlayer.data.search.SearchResultMediaList;
import ch.srg.dataProvider.integrationlayer.data.search.SearchResultShowList;
import ch.srg.dataProvider.integrationlayer.legacy.SearchParams;
import retrofit2.Call;
import retrofit2.http.Path;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public final class LegacyIlDataProvider {
    private static final String UNLIMITED = "unlimited";
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_FORMAT_PATTERN_SHORT = "yyyy-MM-dd";

    @NonNull
    private LegacyIlService ilService;

    public LegacyIlDataProvider(@NonNull LegacyIlService ilService) {
        this.ilService = ilService;
    }

    private static SimpleDateFormat createDateFormat() {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN_SHORT, Locale.US);
    }

    private static SimpleDateFormat createTimeFormat() {
        return new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);
    }

    public static String serializeDate(@NonNull Date date) {
        return createDateFormat().format(date);
    }

    public static String serializeTime(@NonNull Date date) {
        return createTimeFormat().format(date);
    }

    public static String serializeCalendar(@NonNull Calendar date) {
        return String.format(Locale.US, "%04d-%02d-%02d", date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH));
    }

    public static String serializeVendor(@NonNull Vendor vendor) {
        return vendor.name().toLowerCase();
    }

    public static String serializeTransmission(@NonNull Transmission transmission) {
        return transmission.name().toLowerCase();
    }

    @Nullable
    public static String serializeLiveCenterType(LiveCenterType type) {
        return type != null ? type.name().toLowerCase() : null;
    }

    public static <T> String serializeTabs(List<T> list) {
        return TextUtils.join(",", list);
    }

    //region byUrns
    public Call<EpisodeCompositionListResult> getEpisodeComposition(String urn, Integer pageSize) {
        return ilService.getEpisodeComposition(urn, pageSize);
    }

    public LiveData<IlResponse<EpisodeCompositionListResult>> getEpisodeCompositionLiveData(String urn, Integer pageSize) {
        return ilService.getEpisodeCompositionLiveData(urn, pageSize);
    }

    public Call<Media> getMedia(@Path("urn") String urn) {
        return ilService.getMedia(urn);
    }

    public LiveData<IlResponse<Media>> getMediaLiveData(@Path("urn") String urn) {
        return ilService.getMediaLiveData(urn);
    }

    public Call<Show> getShow(String urn) {
        return ilService.getShow(urn);
    }

    public LiveData<IlResponse<Show>> getShowLiveData(String urn) {
        return ilService.getShowLiveData(urn);
    }

    public Call<Channel> getChannel(String channelUrn) {
        return ilService.getChannel(channelUrn);
    }

    public LiveData<IlResponse<Channel>> getNowAndNextLiveData(String channelUrn) {
        return ilService.getChannelLiveData(channelUrn);
    }

    /**
     * @param channelUrn    channel urn not the id.
     * @param liveStreamUrn stream urn useful for multi live stream channel
     * @return Channel with the now and next program if available
     */
    public Call<NowAndNext> getNowAndNext(String channelUrn, @Nullable String liveStreamUrn) {
        return ilService.getNowAndNext(channelUrn, liveStreamUrn);
    }

    /**
     * @param channelUrn    channel urn not the id.
     * @param liveStreamUrn stream urn useful for multi live stream channel
     * @return Channel with the now and next program if available
     */
    public LiveData<IlResponse<NowAndNext>> getNowAndNextLiveData(String channelUrn, @Nullable String liveStreamUrn) {
        return ilService.getNowAndNextLiveData(channelUrn, liveStreamUrn);
    }

    /**
     * @param urns size max 50
     */
    public Call<MediaListResult> getMediaListFromUrns(List<String> urns) {
        return ilService.getMediaListFromUrns(serializeTabs(urns));
    }

    /**
     * @param urns size max 50
     */
    public LiveData<IlResponse<MediaListResult>> getMediaListFromUrnsLiveData(List<String> urns) {
        return ilService.getMediaListFromUrnsLiveData(serializeTabs(urns));
    }

    /**
     * @param showUrns       max element = 15
     * @param filter         {@link MediaFilter}
     * @param maxPublishDate Content is included from now to past until maxPublishedDate.
     * @param minPublishDate A minimal publish date
     * @param pageSize       by default 10
     */
    public Call<MediaListResult> getLatestByShows(@NonNull List<String> showUrns,
                                                  @Nullable MediaFilter filter,
                                                  @Nullable Date maxPublishDate,
                                                  @Nullable Date minPublishDate,
                                                  @Nullable Integer pageSize) {
        Boolean episodeOnly = filter == MediaFilter.EPISODE_ONLY ? true : null;
        Boolean excludeEpisode = filter == MediaFilter.EPISODE_EXCLUDED ? true : null;
        SimpleDateFormat sdf = createDateFormat();
        String maxDate = maxPublishDate != null ? sdf.format(maxPublishDate) : null;
        String minDate = minPublishDate != null ? sdf.format(minPublishDate) : null;
        return ilService.getLatestByShows(serializeTabs(showUrns), episodeOnly, excludeEpisode, maxDate, minDate, null, pageSize);
    }

    /**
     * @param showUrns       max element = 15
     * @param filter         {@link MediaFilter}
     * @param maxPublishDate Content is included from now to past until maxPublishedDate.
     * @param minPublishDate A minimal publish date
     * @param pageSize       by default 10
     */
    public LiveData<IlResponse<MediaListResult>> getLatestByShowsLiveData(@NonNull List<String> showUrns,
                                                                          @Nullable MediaFilter filter,
                                                                          @Nullable Date maxPublishDate,
                                                                          @Nullable Date minPublishDate,
                                                                          @Nullable Integer pageSize) {
        Boolean episodeOnly = filter == MediaFilter.EPISODE_ONLY ? true : null;
        Boolean excludeEpisode = filter == MediaFilter.EPISODE_EXCLUDED ? true : null;
        SimpleDateFormat sdf = createDateFormat();
        String maxDate = maxPublishDate != null ? sdf.format(maxPublishDate) : null;
        String minDate = minPublishDate != null ? sdf.format(minPublishDate) : null;
        return ilService.getLatestByShowsLiveData(serializeTabs(showUrns), episodeOnly, excludeEpisode, maxDate, minDate, null, pageSize);
    }

    /**
     * @param tabUrns size max 50
     */
    public Call<ShowListResult> getShowListFromUrns(List<String> tabUrns) {
        return ilService.getShowListFromUrns(serializeTabs(tabUrns));
    }

    /**
     * @param tabUrns size max 50
     */
    public LiveData<IlResponse<ShowListResult>> getShowListFromUrnsLiveData(List<String> tabUrns) {
        return ilService.getShowListFromUrnsLiveData(serializeTabs(tabUrns));
    }

    public Call<MediaListResult> getLatestTopic(String topicUrn, Integer pageSize) {
        return ilService.getLatestTopic(topicUrn, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getLatestTopicLiveData(String topicUrn, Integer pageSize) {
        return ilService.getLatestTopicLiveData(topicUrn, pageSize);
    }

    public Call<MediaListResult> getMostClickedByTopic(String topicUrn, Integer pageSize) {
        return ilService.getMostClickedByTopic(topicUrn, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getMostClickedByTopicLiveData(String topicUrn, Integer pageSize) {
        return ilService.getMostClickedByTopicLiveData(topicUrn, pageSize);
    }

    public Call<MediaListResult> getLatestByModuleConfig(String moduleConfigUrn, Integer pageSize) {
        return ilService.getLatestByModuleConfig(moduleConfigUrn, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getLatestByModuleConfigLiveData(String moduleConfigUrn, Integer pageSize) {
        return ilService.getLatestByModuleConfigLiveData(moduleConfigUrn, pageSize);
    }

    public Call<MediaListResult> getRecommended(String urn, Integer pageSize) {
        return ilService.getRecommended(urn, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getRecommendedLiveData(String urn, Integer pageSize) {
        return ilService.getRecommendedLiveData(urn, pageSize);
    }
    //endregion byUrns

    //region tv
    public Call<MediaListResult> getVideoLivestreams(@NonNull Vendor vendor) {
        return ilService.getVideoLivestreams(serializeVendor(vendor));
    }

    public LiveData<IlResponse<MediaListResult>> getVideoLivestreamsLiveData(@NonNull Vendor vendor) {
        return ilService.getVideoLivestreamsLiveData(serializeVendor(vendor));
    }

    public Call<MediaListResult> getVideoSportScheduledLiveStream(@NonNull Vendor vendor, LiveCenterType type, boolean onlyEventsWithResult, Integer pageSize) {
        return ilService.getVideoSportScheduledLiveStream(serializeVendor(vendor), serializeLiveCenterType(type), onlyEventsWithResult, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getVideoSportScheduledLiveStreamLiveData(@NonNull Vendor vendor, LiveCenterType type, boolean onlyEventsWithResult, Integer pageSize) {
        return ilService.getVideoSportScheduledLiveStreamLiveData(serializeVendor(vendor), serializeLiveCenterType(type), onlyEventsWithResult, pageSize);
    }

    public Call<MediaListResult> getVideoScheduledLiveStream(@NonNull Vendor vendor, boolean signLanguageOnly, Integer pageSize) {
        return ilService.getVideoScheduledLiveStream(serializeVendor(vendor), signLanguageOnly, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getVideoScheduledLiveStreamLiveData(@NonNull Vendor vendor, boolean signLanguageOnly, Integer pageSize) {
        return ilService.getVideoScheduledLiveStreamLiveData(serializeVendor(vendor), signLanguageOnly, pageSize);
    }

    public Call<MediaListResult> getVideoTrendingAndEditorial(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes) {
        return ilService.getVideoTrendingAndEditorial(serializeVendor(vendor), pageSize, onlyEpisodes);
    }

    public LiveData<IlResponse<MediaListResult>> getVideoTrendingAndEditorialLiveData(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes) {
        return ilService.getVideoTrendingAndEditorialLiveData(serializeVendor(vendor), pageSize, onlyEpisodes);
    }

    public Call<TopicListResult> getTopics(@NonNull Vendor vendor) {
        return ilService.getTopics(serializeVendor(vendor));
    }

    public LiveData<IlResponse<TopicListResult>> getTopicsLiveData(@NonNull Vendor vendor) {
        return ilService.getTopicsLiveData(serializeVendor(vendor));
    }

    public Call<MediaListResult> getMostClickedVideo(@NonNull Vendor vendor, Integer pageSize) {
        return ilService.getMostClickedVideo(serializeVendor(vendor), pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getMostClickedVideoLiveData(@NonNull Vendor vendor, Integer pageSize) {
        return ilService.getMostClickedVideoLiveData(serializeVendor(vendor), pageSize);
    }

    public Call<MediaListResult> getLatestEpisodes(@NonNull Vendor vendor, Integer pageSize) {
        return ilService.getLatestEpisodes(serializeVendor(vendor), pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getLatestEpisodesLiveData(@NonNull Vendor vendor, Integer pageSize) {
        return ilService.getLatestEpisodesLiveData(serializeVendor(vendor), pageSize);
    }

    public Call<MediaListResult> getSoonExpiringVideo(@NonNull Vendor vendor, Integer pageSize) {
        return ilService.getSoonExpiringVideo(serializeVendor(vendor), pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getSoonExpiringVideoLiveData(@NonNull Vendor vendor, Integer pageSize) {
        return ilService.getSoonExpiringVideoLiveData(serializeVendor(vendor), pageSize);
    }

    public Call<MediaListResult> getSoonExpiringVideoForTopic(@NonNull Vendor vendor, String topicId, Integer pageSize) {
        return ilService.getSoonExpiringVideoForTopic(serializeVendor(vendor), topicId, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getSoonExpiringVideoForTopicLiveData(@NonNull Vendor vendor, String topicId, Integer pageSize) {
        return ilService.getSoonExpiringVideoForTopicLiveData(serializeVendor(vendor), topicId, pageSize);
    }

    public Call<ShowListResult> getAlphabeticalTvShowList(@NonNull Vendor vendor, @Nullable Integer pageSize) {
        return ilService.getAlphabeticalTvShowList(serializeVendor(vendor), pageSize != null ? pageSize.toString() : null);
    }

    public LiveData<IlResponse<ShowListResult>> getAlphabeticalTvShowListLiveData(@NonNull Vendor vendor, @Nullable Integer pageSize) {
        return ilService.getAlphabeticalTvShowListLiveData(serializeVendor(vendor), pageSize != null ? pageSize.toString() : null);
    }

    public Call<ShowListResult> getAlphabeticalTvShowListUnlimited(@NonNull Vendor vendor) {
        return ilService.getAlphabeticalTvShowList(serializeVendor(vendor), UNLIMITED);
    }

    public LiveData<IlResponse<ShowListResult>> getAlphabeticalTvShowListUnlimitedLiveData(@NonNull Vendor vendor) {
        return ilService.getAlphabeticalTvShowListLiveData(serializeVendor(vendor), UNLIMITED);
    }

    public Call<MediaListResult> getVideoByDate(@NonNull Vendor vendor, Calendar date, Integer pageSize) {
        return ilService.getVideoByDate(serializeVendor(vendor), serializeCalendar(date), pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getVideoByDateLiveData(@NonNull Vendor vendor, Calendar date, Integer pageSize) {
        return ilService.getVideoByDateLiveData(serializeVendor(vendor), serializeCalendar(date), pageSize);
    }

    public Call<MediaListResult> getVideoWebFirst(Vendor vendor, Integer pageSize) {
        return ilService.getVideoWebFirst(serializeVendor(vendor), pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getVideoWebFirstLiveData(Vendor vendor, Integer pageSize) {
        return ilService.getVideoWebFirstLiveData(serializeVendor(vendor), pageSize);
    }

    public Call<MediaListResult> getVideoHeroStage(Vendor vendor) {
        return ilService.getVideoHeroStage(serializeVendor(vendor));
    }

    public LiveData<IlResponse<MediaListResult>> getVideoHeroStageLiveData(Vendor vendor) {
        return ilService.getVideoHeroStageLiveData(serializeVendor(vendor));
    }
    //endregion tv

    //region radio
    public Call<MediaListResult> getAudioLivestreams(@NonNull Vendor vendor, boolean includeThirdPartyStreams) {
        return ilService.getAudioLivestreams(serializeVendor(vendor), includeThirdPartyStreams);
    }

    public LiveData<IlResponse<MediaListResult>> getAudioLivestreamsLiveData(@NonNull Vendor vendor, boolean includeThirdPartyStreams) {
        return ilService.getAudioLivestreamsLiveData(serializeVendor(vendor), includeThirdPartyStreams);
    }

    public Call<MediaListResult> getAudioLivestreamThirdPartyContent(@NonNull Vendor vendor) {
        return ilService.getAudioLivestreamThirdPartyContent(serializeVendor(vendor));
    }

    public LiveData<IlResponse<MediaListResult>> getAudioLivestreamThirdPartyContentLiveData(@NonNull Vendor vendor) {
        return ilService.getAudioLivestreamThirdPartyContentLiveData(serializeVendor(vendor));
    }

    public Call<MediaListResult> getAudioLivestreamsByChannel(@NonNull Vendor vendor, String channelId) {
        return ilService.getAudioLivestreamsByChannel(serializeVendor(vendor), channelId);
    }

    public LiveData<IlResponse<MediaListResult>> getAudioLivestreamsByChannelLiveData(@NonNull Vendor vendor, String channelId) {
        return ilService.getAudioLivestreamsByChannelLiveData(serializeVendor(vendor), channelId);
    }

    public Call<MediaListResult> getAudioEditorial(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes) {
        return ilService.getAudioEditorial(serializeVendor(vendor), pageSize, onlyEpisodes);
    }

    public LiveData<IlResponse<MediaListResult>> getAudioEditorialLiveData(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes) {
        return ilService.getAudioEditorialLiveData(serializeVendor(vendor), pageSize, onlyEpisodes);
    }

    public Call<MediaListResult> getMostClickedAudioForChannel(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getMostClickedAudioForChannel(serializeVendor(vendor), channelId, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getMostClickedAudioForChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getMostClickedAudioForChannelLiveData(serializeVendor(vendor), channelId, pageSize);
    }

    public Call<MediaListResult> getMostClickedAudio(@NonNull Vendor vendor, boolean onlyEpisodes, Integer pageSize) {
        return ilService.getMostClickedAudio(serializeVendor(vendor), onlyEpisodes, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getMostClickedAudioLiveData(@NonNull Vendor vendor, boolean onlyEpisodes, Integer pageSize) {
        return ilService.getMostClickedAudioLiveData(serializeVendor(vendor), onlyEpisodes, pageSize);
    }

    public Call<MediaListResult> getLatestAudioForChannel(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getLatestAudioForChannel(serializeVendor(vendor), channelId, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getLatestAudioForChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getLatestAudioForChannelLiveData(serializeVendor(vendor), channelId, pageSize);
    }

    public Call<MediaListResult> getLatestAudioEpisodesForChannel(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getLatestAudioEpisodesForChannel(serializeVendor(vendor), channelId, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getLatestAudioEpisodesForChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getLatestAudioEpisodesForChannelLiveData(serializeVendor(vendor), channelId, pageSize);
    }

    public Call<MediaListResult> getVideoLatestByChannel(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getVideoLatestByChannel(serializeVendor(vendor), channelId, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getVideoLatestByChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getVideoLatestByChannelLiveData(serializeVendor(vendor), channelId, pageSize);
    }

    public Call<ShowListResult> getAlphabeticalRadioShowList(@NonNull Vendor vendor, String channelId, @Nullable Integer pageSize) {
        return ilService.getAlphabeticalRadioShowList(serializeVendor(vendor), channelId, pageSize != null ? pageSize.toString() : null);
    }

    public LiveData<IlResponse<ShowListResult>> getAlphabeticalRadioShowListLiveData(@NonNull Vendor vendor, String channelId, @Nullable Integer pageSize) {
        return ilService.getAlphabeticalRadioShowListLiveData(serializeVendor(vendor), channelId, pageSize != null ? pageSize.toString() : null);
    }

    public Call<ShowListResult> getAlphabeticalRadioShowListUnlimited(@NonNull Vendor vendor, String channelId) {
        return ilService.getAlphabeticalRadioShowList(serializeVendor(vendor), channelId, UNLIMITED);
    }

    public LiveData<IlResponse<ShowListResult>> getAlphabeticalRadioShowListUnlimitedLiveData(@NonNull Vendor vendor, String channelId) {
        return ilService.getAlphabeticalRadioShowListLiveData(serializeVendor(vendor), channelId, UNLIMITED);
    }

    public Call<MediaListResult> getAudioByDate(@NonNull Vendor vendor, Calendar date, String channelId, Integer pageSize) {
        return ilService.getAudioByDate(serializeVendor(vendor), serializeCalendar(date), channelId, pageSize);
    }

    public LiveData<IlResponse<MediaListResult>> getAudioByDateLiveData(@NonNull Vendor vendor, Calendar date, String channelId, Integer pageSize) {
        return ilService.getAudioByDateLiveData(serializeVendor(vendor), serializeCalendar(date), channelId, pageSize);
    }

    public Call<SongListResult> getSongList(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getSongList(serializeVendor(vendor), channelId, pageSize.toString());
    }

    public LiveData<IlResponse<SongListResult>> getSongListLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilService.getSongListLiveData(serializeVendor(vendor), channelId, pageSize.toString());
    }

    public Call<SongListResult> getSongListUnlimited(@NonNull Vendor vendor, String channelId) {
        return ilService.getSongList(serializeVendor(vendor), channelId, UNLIMITED);
    }

    public LiveData<IlResponse<SongListResult>> getCurrentSongListUnilimitedLiveData(@NonNull Vendor vendor, String channelId) {
        return ilService.getSongListLiveData(serializeVendor(vendor), channelId, UNLIMITED);
    }

    //endregion radio

    //region search
    public Call<ShowListResult> getMostClickedSearchedShow(@NonNull Vendor vendor) {
        return ilService.getMostClickedSearchedShow(serializeVendor(vendor));
    }

    public LiveData<IlResponse<ShowListResult>> getMostClickedSearchedShowLiveData(@NonNull Vendor vendor) {
        return ilService.getMostClickedSearchedShowLiveData(serializeVendor(vendor));
    }

    /**
     * Search media.
     *
     * @param searchTerm search term (can be empty)
     * @param params     list of query parameters to send to the server. <p>
     *                   Note that SWI does not support any query parameters (May 2019)
     * @return SearchResultMediaList with the first page result
     */
    public Call<SearchResultMediaList> searchMedia(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.MediaParams params) {
        return ilService.searchMedia(serializeVendor(vendor), searchTerm, params.toQueryMap());
    }

    /**
     * Search media.
     *
     * @param searchTerm search term (can be empty)
     * @param params     list of query parameters to send to the server. <p>
     *                   Note that SWI does not support any query parameters (May 2019)
     * @return LiveData with the first page result
     */
    public LiveData<IlResponse<SearchResultMediaList>> searchMediaLiveData(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.MediaParams params) {
        return ilService.searchMediaLiveData(serializeVendor(vendor), searchTerm, params.toQueryMap());
    }

    public Call<SearchResultShowList> searchShow(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.ShowParams params) {
        return ilService.searchShow(serializeVendor(vendor), searchTerm, params.toQueryMap());
    }

    public LiveData<IlResponse<SearchResultShowList>> searchShowLiveData(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.ShowParams params) {
        return ilService.searchShowLiveData(serializeVendor(vendor), searchTerm, params.toQueryMap());
    }
    //endregion search

    //region buSpecific

    /**
     * @param vendor       vendor only for (srf,rts,rsi,rts)
     * @param transmission transmission (RADIO or TV)
     * @param channelId    Channel id not the urn
     * @param minEndTime   Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param maxStartTime Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param liveStreamId id of a regional journal live stream id, not urn!
     */
    public Call<ProgramCompositionListResult> programList(@NonNull Vendor vendor,
                                                          @NonNull Transmission transmission,
                                                          String channelId,
                                                          Integer pageSize,
                                                          @Nullable Date minEndTime, @Nullable Date maxStartTime,
                                                          @Nullable String liveStreamId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);
        String minEndTimeValue = minEndTime != null ? dateFormat.format(minEndTime) : null;
        String maxStartTimeValue = maxStartTime != null ? dateFormat.format(maxStartTime) : null;
        return ilService.programList(serializeVendor(vendor), serializeTransmission(transmission), channelId, pageSize, minEndTimeValue, maxStartTimeValue, liveStreamId);
    }

    /**
     * @param vendor       vendor only for (srf,rts,rsi,rts)
     * @param transmission transmission (RADIO or TV)
     * @param channelId    Channel id not the urn
     * @param minEndTime   Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param maxStartTime Format: ‘yyyy-MM-ddTHH:mm:ss’
     * @param liveStreamId id of a regional journal live stream id, not urn!
     */
    public LiveData<IlResponse<ProgramCompositionListResult>> programListLiveData(@NonNull Vendor vendor,
                                                                                  @NonNull Transmission transmission,
                                                                                  String channelId,
                                                                                  Integer pageSize,
                                                                                  @Nullable Date minEndTime, @Nullable Date maxStartTime,
                                                                                  @Nullable String liveStreamId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.US);
        String minEndTimeValue = minEndTime != null ? dateFormat.format(minEndTime) : null;
        String maxStartTimeValue = maxStartTime != null ? dateFormat.format(maxStartTime) : null;
        return ilService.programListLiveData(serializeVendor(vendor), serializeTransmission(transmission), channelId, pageSize, minEndTimeValue, maxStartTimeValue, liveStreamId);
    }

    /**
     * @param vendor vendor only for (srf,rts,rsi,rts)
     * @param date   A ISO-8601 date (YYYY-MM-DD).
     */
    public Call<ProgramGuide> programGuide(@NonNull Vendor vendor, @NonNull Date date) {
        return ilService.programGuide(serializeVendor(vendor), serializeDate(date), false);
    }

    /**
     * @param vendor  vendor only for (srf,rts,rsi,rts)
     * @param date    A ISO-8601 date (YYYY-MM-DD).
     * @param reduced When set to true, only basic program information is returned (Default value: false).
     */
    public Call<ProgramGuide> programGuide(@NonNull Vendor vendor, @NonNull Date date, boolean reduced) {
        return ilService.programGuide(serializeVendor(vendor), serializeDate(date), reduced);
    }

    /**
     * @param vendor vendor only for (srf,rts,rsi,rts)
     * @param date   A ISO-8601 date (YYYY-MM-DD).
     */
    public LiveData<IlResponse<ProgramGuide>> programGuideLiveData(@NonNull Vendor vendor, @NonNull Date date) {
        return ilService.programGuideLiveData(serializeVendor(vendor), serializeDate(date), false);
    }

    /**
     * @param vendor  vendor only for (srf,rts,rsi,rts)
     * @param date    A ISO-8601 date (YYYY-MM-DD).
     * @param reduced When set to true, only basic program information is returned (Default value: false).
     */
    public LiveData<IlResponse<ProgramGuide>> programGuideLiveData(@NonNull Vendor vendor, @NonNull Date date, boolean reduced) {
        return ilService.programGuideLiveData(serializeVendor(vendor), serializeDate(date), reduced);
    }

    /**
     * @param vendor vendor only for (srf,rts,rsi,rts)
     * @param date   A ISO-8601 date (YYYY-MM-DD).
     */
    public Call<ProgramGuide> programGuideNonSrg(@NonNull Vendor vendor, @NonNull Date date) {
        return ilService.programGuideNonSrg(serializeVendor(vendor), serializeDate(date), false);
    }

    /**
     * @param vendor  vendor only for (srf,rts,rsi,rts)
     * @param date    A ISO-8601 date (YYYY-MM-DD).
     * @param reduced When set to true, only basic program information is returned (Default value: false).
     */
    public Call<ProgramGuide> programGuideNonSrg(@NonNull Vendor vendor, @NonNull Date date, boolean reduced) {
        return ilService.programGuideNonSrg(serializeVendor(vendor), serializeDate(date), reduced);
    }

    /**
     * @param vendor vendor only for (srf,rts,rsi,rts)
     * @param date   A ISO-8601 date (YYYY-MM-DD).
     */
    public LiveData<IlResponse<ProgramGuide>> programGuideNonSrgLiveData(@NonNull Vendor vendor, @NonNull Date date) {
        return ilService.programGuideNonSrgLiveData(serializeVendor(vendor), serializeDate(date), false);
    }

    /**
     * @param vendor  vendor only for (srf,rts,rsi,rts)
     * @param date    A ISO-8601 date (YYYY-MM-DD).
     * @param reduced When set to true, only basic program information is returned (Default value: false).
     */
    public LiveData<IlResponse<ProgramGuide>> programGuideNonSrgLiveData(@NonNull Vendor vendor, @NonNull Date date, boolean reduced) {
        return ilService.programGuideNonSrgLiveData(serializeVendor(vendor), serializeDate(date), reduced);
    }

    //endregion buSpecific

    //region nextUrls
    public Call<MediaListResult> getMediaListNextUrl(String url) {
        return ilService.getMediaListNextUrl(url);
    }

    public LiveData<IlResponse<MediaListResult>> getMediaListNextUrlLiveData(String url) {
        return ilService.getMediaListNextUrlLiveData(url);
    }

    public Call<EpisodeCompositionListResult> getEpisodeCompositionNextUrl(String url) {
        return ilService.getEpisodeCompositionNextUrl(url);
    }

    public LiveData<IlResponse<EpisodeCompositionListResult>> getEpisodeCompositionNextUrlLiveData(String url) {
        return ilService.getEpisodeCompositionNextUrlLiveData(url);
    }

    public Call<ShowListResult> getShowListNextUrl(String url) {
        return ilService.getShowListNextUrl(url);
    }

    public LiveData<IlResponse<ShowListResult>> getShowListNextUrlLiveData(String url) {
        return ilService.getShowListNextUrlLiveData(url);
    }

    public Call<SongListResult> getSongListNextUrl(String url) {
        return ilService.getSongListNextUrl(url);
    }

    public LiveData<IlResponse<SongListResult>> getSongListNextUrlLiveData(String url) {
        return ilService.getSongListNextUrlLiveData(url);
    }

    public Call<SearchResultMediaList> searchMediaNextUrl(String url) {
        return ilService.searchMediaNextUrl(url);
    }

    public LiveData<IlResponse<SearchResultMediaList>> searchMediaNextUrlLiveData(String url) {
        return ilService.searchMediaNextUrlLiveData(url);
    }

    public Call<SearchResultShowList> searchShowNextUrl(String url) {
        return ilService.searchShowNextUrl(url);
    }

    public LiveData<IlResponse<SearchResultShowList>> searchShowNextUrlLiveData(String url) {
        return ilService.searchShowNextUrlLiveData(url);
    }
    //endregion nextUrls

    //region statistics

    /**
     * @param chapter with a not empty {@link Chapter#getEventData()}
     * @param userId  from the il if needed
     * @return corresponding call if it is trigger, null otherwise
     */
    @Nullable
    public Call<Object> postMediaClicked(@NonNull Chapter chapter, @Nullable String userId) {
        if (chapter.getEventData() != null && !TextUtils.isEmpty(chapter.getEventData())) {
            MediaStatisticPost mediaStatisticsBody = new MediaStatisticPost(chapter.getEventData(), userId);
            return ilService.postMediaClicked(chapter.getUrn(), mediaStatisticsBody);
        } else {
            return null;
        }
    }

    /**
     * @param chapter with a not empty {@link Chapter#getEventData()}
     * @param userId  from the il if needed
     * @return corresponding call if it is trigger, null otherwise
     */
    @Nullable
    public Call<Object> postMediaLiked(@NonNull Chapter chapter, @Nullable String userId) {
        if (!TextUtils.isEmpty(chapter.getEventData())) {
            MediaStatisticPost mediaStatisticsBody = new MediaStatisticPost(chapter.getEventData(), userId);
            return ilService.postMediaLiked(chapter.getUrn(), mediaStatisticsBody);
        } else {
            return null;
        }
    }
    //endregion statistics

    //region pac
    public Call<Page> getLandingPageByProduct(@NonNull Vendor vendor, @NonNull String product, boolean isPublished) {
        return ilService.getLandingPageByProduct(serializeVendor(vendor), product, isPublished);
    }

    public LiveData<IlResponse<Page>> getLandingPageByProductLiveData(@NonNull Vendor vendor, @NonNull String product, boolean isPublished) {
        return ilService.getLandingPageByProductLiveData(serializeVendor(vendor), product, isPublished);
    }

    public Call<Page> getPage(@NonNull Vendor vendor, String pageId, boolean isPublished) {
        return ilService.getPage(serializeVendor(vendor), pageId, isPublished);
    }

    public LiveData<IlResponse<Page>> getPageLiveData(@NonNull Vendor vendor, String pageId, boolean isPublished) {
        return ilService.getPageLiveData(serializeVendor(vendor), pageId, isPublished);
    }

    public Call<Section> getSection(@NonNull Vendor vendor, String sectionId, boolean isPublished) {
        return ilService.getSection(serializeVendor(vendor), sectionId, isPublished);
    }

    public LiveData<IlResponse<Section>> getSectionLiveData(@NonNull Vendor vendor, String pageId, boolean isPublished) {
        return ilService.getSectionLiveData(serializeVendor(vendor), pageId, isPublished);
    }

    public Call<Page> getPageForTopic(@NonNull Vendor vendor, String topicUrn, boolean isPublished) {
        return ilService.getPageForTopic(serializeVendor(vendor), topicUrn, isPublished);
    }

    public LiveData<IlResponse<Page>> getPageForTopicLiveData(@NonNull Vendor vendor, String topicUrn, boolean isPublished) {
        return ilService.getPageForTopicLiveData(serializeVendor(vendor), topicUrn, isPublished);
    }

    public Call<MediaListResult> getMediaListForMediaSection(@NonNull Vendor vendor, String sectionId, boolean isPublished) {
        return ilService.getMediaListForMediaSection(serializeVendor(vendor), sectionId, isPublished);
    }

    public LiveData<IlResponse<MediaListResult>> getMediaListForMediaSectionLiveData(@NonNull Vendor vendor, String sectionId, boolean isPublished) {
        return ilService.getMediaListForMediaSectionLiveData(serializeVendor(vendor), sectionId, isPublished);
    }

    public Call<MediaListWithShowResult> getMediaListForMediaSectionWithShow(@NonNull Vendor vendor, String sectionId, boolean isPublished) {
        return ilService.getMediaListForMediaSectionWithShow(serializeVendor(vendor), sectionId, isPublished);
    }

    public LiveData<IlResponse<MediaListWithShowResult>> getMediaListForMediaSectionWithShowLiveData(@NonNull Vendor vendor, String sectionId, boolean isPublished) {
        return ilService.getMediaListForMediaSectionWithShowLiveData(serializeVendor(vendor), sectionId, isPublished);
    }

    public Call<ShowListResult> getShowListForShowSection(@NonNull Vendor vendor, String sectionId, boolean isPublished) {
        return ilService.getShowListForShowSection(serializeVendor(vendor), sectionId, isPublished);
    }

    public LiveData<IlResponse<ShowListResult>> getShowListForShowSectionLiveData(@NonNull Vendor vendor, String sectionId, boolean isPublished) {
        return ilService.getShowListForShowSectionLiveData(serializeVendor(vendor), sectionId, isPublished);
    }
    //endregion pac
}
