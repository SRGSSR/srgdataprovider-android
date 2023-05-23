package ch.srg.dataProvider.integrationlayer.legacy;

import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ch.srg.dataProvider.integrationlayer.data.Channel;
import ch.srg.dataProvider.integrationlayer.data.Chapter;
import ch.srg.dataProvider.integrationlayer.data.EpisodeCompositionListResult;
import ch.srg.dataProvider.integrationlayer.data.LiveCenterType;
import ch.srg.dataProvider.integrationlayer.data.Media;
import ch.srg.dataProvider.integrationlayer.data.MediaFilter;
import ch.srg.dataProvider.integrationlayer.data.MediaListResult;
import ch.srg.dataProvider.integrationlayer.data.MediaListWithShowResult;
import ch.srg.dataProvider.integrationlayer.data.NowAndNext;
import ch.srg.dataProvider.integrationlayer.data.ProgramCompositionListResult;
import ch.srg.dataProvider.integrationlayer.data.ProgramGuide;
import ch.srg.dataProvider.integrationlayer.data.Show;
import ch.srg.dataProvider.integrationlayer.data.ShowListResult;
import ch.srg.dataProvider.integrationlayer.data.SongListResult;
import ch.srg.dataProvider.integrationlayer.data.Topic;
import ch.srg.dataProvider.integrationlayer.data.TopicListResult;
import ch.srg.dataProvider.integrationlayer.data.Transmission;
import ch.srg.dataProvider.integrationlayer.data.Vendor;
import ch.srg.dataProvider.integrationlayer.data.content.Page;
import ch.srg.dataProvider.integrationlayer.data.content.Section;
import ch.srg.dataProvider.integrationlayer.data.search.SearchResultMediaList;
import ch.srg.dataProvider.integrationlayer.data.search.SearchResultShowList;
import ch.srg.dataProvider.integrationlayer.legacy.requests.IlResponse;
import ch.srg.dataProvider.integrationlayer.legacy.requests.LegacyIlDataProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class LegacyIlDataProviderRemote {
    private static final int MAX_SHOW_LIST_REQUEST_SIZE = 50;
    private static final int MAX_MEDIA_LIST_REQUEST_SIZE = 50;

    @NonNull
    private final LegacyIlDataProvider ilDataProvider;

    public LegacyIlDataProviderRemote(@NonNull LegacyIlDataProvider ilDataProvider) {
        this.ilDataProvider = ilDataProvider;
    }

    public static ArrayList<List<String>> splitUrns(List<String> listUrn, int bucketMaxSize) {
        ArrayList<List<String>> bucketList = new ArrayList<>();
        ArrayList<String> bucket = null;
        for (String urn : listUrn) {
            if (bucket == null || bucket.size() >= bucketMaxSize) {
                bucket = new ArrayList<>(bucketMaxSize);
                bucketList.add(bucket);
            }
            bucket.add(urn);
        }
        return bucketList;
    }

    private static <T> Cancellable enqueue(Call<T> call, IlDataProviderCallback<T> callback) {
        call.enqueue(callback);
        return call::cancel;
    }

    //region byUrns

    public Cancellable getEpisodeComposition(String urn, Integer pageSize, @NonNull IlDataProviderCallback<EpisodeCompositionListResult> callback) {
        return enqueue(ilDataProvider.getEpisodeComposition(urn, pageSize), callback);
    }


    public LiveData<IlResponse<EpisodeCompositionListResult>> getEpisodeCompositionLiveData(String urn, Integer pageSize) {
        return ilDataProvider.getEpisodeCompositionLiveData(urn, pageSize);
    }


    public Cancellable getMedia(String urn, @NonNull IlDataProviderCallback<Media> callback) {
        return enqueue(ilDataProvider.getMedia(urn), callback);
    }


    public LiveData<IlResponse<Media>> getMediaLiveData(String urn) {
        return ilDataProvider.getMediaLiveData(urn);
    }


    public Cancellable getShow(String urn, @NonNull IlDataProviderCallback<Show> callback) {
        return enqueue(ilDataProvider.getShow(urn), callback);
    }


    public LiveData<IlResponse<Show>> getShowLiveData(String urn) {
        return ilDataProvider.getShowLiveData(urn);
    }


    public Cancellable getChannel(String channelUrn, @NonNull IlDataProviderCallback<Channel> callback) {
        return enqueue(ilDataProvider.getChannel(channelUrn), callback);
    }

    public LiveData<IlResponse<Channel>> getChannelLiveData(String channelUrn) {
        return ilDataProvider.getNowAndNextLiveData(channelUrn);
    }

    public Cancellable getNowAndNext(String channelUrn, @Nullable String liveStreamUrn, @NonNull IlDataProviderCallback<NowAndNext> callback) {
        return enqueue(ilDataProvider.getNowAndNext(channelUrn, liveStreamUrn), callback);
    }

    public LiveData<IlResponse<NowAndNext>> getNowAndNextLiveData(String channelUrn, @Nullable String liveStreamUrn) {
        return ilDataProvider.getNowAndNextLiveData(channelUrn, liveStreamUrn);
    }

    /**
     * @param listUrns max 50 elements
     */
    public Cancellable getMediaListFromUrns(@NonNull List<String> listUrns, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getMediaListFromUrns(listUrns), callback);
    }

    /**
     * @param listUrns max 50 elements
     */

    public LiveData<IlResponse<MediaListResult>> getMediaListFromUrnsLiveData(@NonNull List<String> listUrns) {
        return ilDataProvider.getMediaListFromUrnsLiveData(listUrns);
    }

    /**
     * @param listShowUrns max 15 elements
     */

    public Cancellable getLatestByShows(@NonNull List<String> listShowUrns,
                                        @Nullable MediaFilter filter,
                                        @Nullable Date maxPublishDate,
                                        @Nullable Date minPublishDate,
                                        @Nullable Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getLatestByShows(listShowUrns, filter, maxPublishDate, minPublishDate, pageSize), callback);
    }

    /**
     * @param listShowUrns max 15 elements
     */

    public LiveData<IlResponse<MediaListResult>> getLatestByShowsLiveData(@NonNull List<String> listShowUrns,
                                                                          @Nullable MediaFilter filter,
                                                                          @Nullable Date maxPublishDate,
                                                                          @Nullable Date minPublishDate,
                                                                          @Nullable Integer pageSize) {
        return ilDataProvider.getLatestByShowsLiveData(listShowUrns, filter, maxPublishDate, minPublishDate, pageSize);
    }


    public Cancellable getShowListFromUrns(@NonNull List<String> listUrn, @NonNull final GetShowListPartialCallback callback) {
        final ArrayList<List<String>> listUrnSplit = splitUrns(listUrn, MAX_SHOW_LIST_REQUEST_SIZE);
        final ArrayList<Call<ShowListResult>> listCall = new ArrayList<>(listUrnSplit.size());

        Callback<ShowListResult> retrofitCallback = new Callback<ShowListResult>() {
            boolean isCanceled = false;
            boolean isFailure = false;

            ArrayList<Show> resultList = new ArrayList<>();
            int callReceived = 0;


            public void onResponse(@NonNull Call<ShowListResult> call, Response<ShowListResult> response) {
                if (response.isSuccessful()) {
                    ShowListResult result = response.body();
                    if (result != null) {
                        ++callReceived;
                        resultList.addAll(result.getList());
                        callback.onShowListLoaded(resultList, callReceived < listCall.size());
                    } else {
                        notifyFailure();
                    }
                } else {
                    notifyFailure();
                }
            }


            public void onFailure(@NonNull Call<ShowListResult> call, Throwable t) {
                if (call.isCanceled()) {
                    notifyCanceled();
                } else {
                    notifyFailure();
                }
            }

            private void notifyCanceled() {
                if (!isCanceled) {
                    isCanceled = true;
                    callback.onShowListCallCancelled();
                }
            }

            private void notifyFailure() {
                if (!isFailure) {
                    isFailure = true;
                    callback.onShowListNotAvailable();
                }
            }
        };
        for (List<String> splitUrns : listUrnSplit) {
            if (!splitUrns.isEmpty()) {
                final Call<ShowListResult> call = ilDataProvider.getShowListFromUrns(splitUrns);
                listCall.add(call);
                call.enqueue(retrofitCallback);
            }
        }

        return () -> {
            for (Call<ShowListResult> call : listCall) {
                call.cancel();
            }
        };
    }


    @NonNull
    public LiveData<IlResponse<List<Show>>> getShowListFromUrnsLiveData(@NonNull List<String> listUrn) {
        MutableLiveData<IlResponse<List<Show>>> liveDataResult = new MutableLiveData<>();
        if (listUrn.isEmpty()) {
            liveDataResult.postValue(new IlResponse<>(Collections.emptyList(), 200, null, IlResponse.Status.SUCCESS));
            return liveDataResult;
        }
        liveDataResult.postValue(new IlResponse<>(Collections.emptyList(), 200, null, IlResponse.Status.LOADING));
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            ArrayList<List<String>> listUrnSplit = splitUrns(listUrn, MAX_SHOW_LIST_REQUEST_SIZE);
            ArrayList<Show> resultList = new ArrayList<>(listUrn.size());
            for (int i = 0; i < listUrnSplit.size(); i++) {
                List<String> splitUrns = listUrnSplit.get(i);
                final Call<ShowListResult> call = ilDataProvider.getShowListFromUrns(splitUrns);
                try {
                    Response<ShowListResult> response = call.execute();
                    if (response.isSuccessful() && response.body() != null) {
                        resultList.addAll(response.body().getList());
                        boolean last = i == listUrnSplit.size() - 1;
                        liveDataResult.postValue(new IlResponse<>(resultList, response.code(), null, last ? IlResponse.Status.SUCCESS : IlResponse.Status.LOADING));
                    } else {
                        liveDataResult.postValue(new IlResponse<>(resultList, response.code(), null, IlResponse.Status.ERROR));
                        break;
                    }
                } catch (IOException | HttpException e) {
                    liveDataResult.postValue(new IlResponse<>(resultList, 0, e, IlResponse.Status.ERROR));
                    break;
                }
            }
        });

        return liveDataResult;
    }


    public Cancellable getLatestTopic(String topicUrn, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getLatestTopic(topicUrn, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getLatestTopicLiveData(String topicUrn, Integer pageSize) {
        return ilDataProvider.getLatestTopicLiveData(topicUrn, pageSize);
    }


    public Cancellable getMostClickedByTopic(String topicUrn, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getMostClickedByTopic(topicUrn, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getMostClickedByTopicLiveData(String topicUrn, Integer pageSize) {
        return ilDataProvider.getMostClickedByTopicLiveData(topicUrn, pageSize);
    }


    public Cancellable getLatestByModuleConfig(String moduleConfigUrn, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getLatestByModuleConfig(moduleConfigUrn, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getLatestByModuleConfigLiveData(String moduleConfigUrn, Integer pageSize) {
        return ilDataProvider.getLatestByModuleConfigLiveData(moduleConfigUrn, pageSize);
    }


    public Cancellable getRecommended(String urn, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getRecommended(urn, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getRecommendedLiveData(String urn, Integer pageSize) {
        return ilDataProvider.getRecommendedLiveData(urn, pageSize);
    }
    //endregion byUrns

    //region tv

    public Cancellable getVideoLivestreams(@NonNull Vendor vendor, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoLivestreams(vendor), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoLivestreamsLiveData(@NonNull Vendor vendor) {
        return ilDataProvider.getVideoLivestreamsLiveData(vendor);
    }


    public Cancellable getVideoSportScheduledLiveStream(@NonNull Vendor vendor,
                                                        LiveCenterType type,
                                                        boolean onlyEventsWithResult,
                                                        Integer pageSize,
                                                        @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoSportScheduledLiveStream(vendor, type, onlyEventsWithResult, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoSportScheduledLiveStreamLiveData(@NonNull Vendor vendor,
                                                                                          LiveCenterType type,
                                                                                          boolean onlyEventsWithResult,
                                                                                          Integer pageSize) {
        return ilDataProvider.getVideoSportScheduledLiveStreamLiveData(vendor, type, onlyEventsWithResult, pageSize);
    }


    public Cancellable getVideoScheduledLiveStream(@NonNull Vendor vendor, boolean signLanguageOnly, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoScheduledLiveStream(vendor, signLanguageOnly, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoScheduledLiveStreamLiveData(@NonNull Vendor vendor, boolean signLanguageOnly, Integer pageSize) {
        return ilDataProvider.getVideoScheduledLiveStreamLiveData(vendor, signLanguageOnly, pageSize);
    }


    public Cancellable getVideoTrendingAndEditorial(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoTrendingAndEditorial(vendor, pageSize, onlyEpisodes), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoTrendingAndEditorialLiveData(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes) {
        return ilDataProvider.getVideoTrendingAndEditorialLiveData(vendor, pageSize, onlyEpisodes);
    }


    public Cancellable getTopics(@NonNull Vendor vendor, @NonNull IlDataProviderCallback<TopicListResult> callback) {
        return enqueue(ilDataProvider.getTopics(vendor), callback);
    }


    public LiveData<IlResponse<TopicListResult>> getTopicsLiveData(@NonNull Vendor vendor) {
        return ilDataProvider.getTopicsLiveData(vendor);
    }


    public Cancellable getMostClickedVideo(@NonNull Vendor vendor, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getMostClickedVideo(vendor, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getMostClickedVideoLiveData(@NonNull Vendor vendor, Integer pageSize) {
        return ilDataProvider.getMostClickedVideoLiveData(vendor, pageSize);
    }


    public Cancellable getLatestEpisodes(@NonNull Vendor vendor, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getLatestEpisodes(vendor, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getLatestEpisodesLiveData(@NonNull Vendor vendor, Integer pageSize) {
        return ilDataProvider.getLatestEpisodesLiveData(vendor, pageSize);
    }


    public Cancellable getSoonExpiringVideo(@NonNull Vendor vendor, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getSoonExpiringVideo(vendor, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getSoonExpiringVideoLiveData(@NonNull Vendor vendor, Integer pageSize) {
        return ilDataProvider.getSoonExpiringVideoLiveData(vendor, pageSize);
    }


    public Cancellable getSoonExpiringVideoForTopic(@NonNull Vendor vendor, String topicId, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getSoonExpiringVideoForTopic(vendor, topicId, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getSoonExpiringVideoForTopicLiveData(@NonNull Vendor vendor, String topicId, Integer pageSize) {
        return ilDataProvider.getSoonExpiringVideoForTopicLiveData(vendor, topicId, pageSize);
    }


    public Cancellable getAlphabeticalTvShowList(@NonNull Vendor vendor, @Nullable Integer pageSize, @NonNull IlDataProviderCallback<ShowListResult> callback) {
        return enqueue(ilDataProvider.getAlphabeticalTvShowList(vendor, pageSize), callback);
    }


    public LiveData<IlResponse<ShowListResult>> getAlphabeticalTvShowListLiveData(@NonNull Vendor vendor, @Nullable Integer pageSize) {
        return ilDataProvider.getAlphabeticalTvShowListLiveData(vendor, pageSize);
    }


    public Cancellable getAlphabeticalTvShowListUnlimited(@NonNull Vendor vendor, @NonNull IlDataProviderCallback<ShowListResult> callback) {
        return enqueue(ilDataProvider.getAlphabeticalTvShowListUnlimited(vendor), callback);
    }


    public LiveData<IlResponse<ShowListResult>> getAlphabeticalTvShowListUnlimitedLiveData(@NonNull Vendor vendor) {
        return ilDataProvider.getAlphabeticalTvShowListUnlimitedLiveData(vendor);
    }


    public Cancellable getVideoByDate(@NonNull Vendor vendor, Calendar date, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoByDate(vendor, date, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoByDateLiveData(@NonNull Vendor vendor, Calendar date, Integer pageSize) {
        return ilDataProvider.getVideoByDateLiveData(vendor, date, pageSize);
    }


    public Cancellable getVideoWebFirst(@NonNull Vendor vendor, @Nullable Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoWebFirst(vendor, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoWebFirstLiveData(@NonNull Vendor vendor, @Nullable Integer pageSize) {
        return ilDataProvider.getVideoWebFirstLiveData(vendor, pageSize);
    }


    public Cancellable getVideoHeroStage(@NonNull Vendor vendor, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoHeroStage(vendor), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoHeroStageLiveData(@NonNull Vendor vendor) {
        return ilDataProvider.getVideoHeroStageLiveData(vendor);
    }
    //endregion tv

    //region radio

    public Cancellable getAudioLivestreams(@NonNull Vendor vendor, boolean includeThirdPartyStreams, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getAudioLivestreams(vendor, includeThirdPartyStreams), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getAudioLivestreamsLiveData(@NonNull Vendor vendor, boolean includeThirdPartyStreams) {
        return ilDataProvider.getAudioLivestreamsLiveData(vendor, includeThirdPartyStreams);
    }


    public Cancellable getAudioLivestreamThirdPartyContent(@NonNull Vendor vendor, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getAudioLivestreamThirdPartyContent(vendor), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getAudioLivestreamThirdPartyContentLiveData(@NonNull Vendor vendor) {
        return ilDataProvider.getAudioLivestreamThirdPartyContentLiveData(vendor);
    }


    public Cancellable getAudioLivestreamsByChannel(@NonNull Vendor vendor, String channelId, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getAudioLivestreamsByChannel(vendor, channelId), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getAudioLivestreamsByChannelLiveData(@NonNull Vendor vendor, String channelId) {
        return ilDataProvider.getAudioLivestreamsByChannelLiveData(vendor, channelId);
    }


    public Cancellable getAudioEditorial(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getAudioEditorial(vendor, pageSize, onlyEpisodes), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getAudioEditorialLiveData(@NonNull Vendor vendor, Integer pageSize, boolean onlyEpisodes) {
        return ilDataProvider.getAudioEditorialLiveData(vendor, pageSize, onlyEpisodes);
    }


    public Cancellable getMostClickedAudioForChannel(@NonNull Vendor vendor, String channelId, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getMostClickedAudioForChannel(vendor, channelId, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getMostClickedAudioForChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilDataProvider.getMostClickedAudioForChannelLiveData(vendor, channelId, pageSize);
    }


    public Cancellable getMostClickedAudio(@NonNull Vendor vendor, boolean onlyEpisodes, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getMostClickedAudio(vendor, onlyEpisodes, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getMostClickedAudioLiveData(@NonNull Vendor vendor, boolean onlyEpisodes, Integer pageSize) {
        return ilDataProvider.getMostClickedAudioLiveData(vendor, onlyEpisodes, pageSize);
    }


    public Cancellable getLatestAudioForChannel(@NonNull Vendor vendor, String channelId, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getLatestAudioForChannel(vendor, channelId, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getLatestAudioForChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilDataProvider.getLatestAudioForChannelLiveData(vendor, channelId, pageSize);
    }


    public Cancellable getLatestAudioEpisodesForChannel(@NonNull Vendor vendor, String channelId, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getLatestAudioEpisodesForChannel(vendor, channelId, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getLatestAudioEpisodesForChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilDataProvider.getLatestAudioEpisodesForChannelLiveData(vendor, channelId, pageSize);
    }


    public Cancellable getVideoLatestByChannel(@NonNull Vendor vendor, String channelId, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getVideoLatestByChannel(vendor, channelId, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getVideoLatestByChannelLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilDataProvider.getVideoLatestByChannelLiveData(vendor, channelId, pageSize);
    }


    public Cancellable getAlphabeticalRadioShowList(@NonNull Vendor vendor, String channelId, @Nullable Integer pageSize, @NonNull IlDataProviderCallback<ShowListResult> callback) {
        return enqueue(ilDataProvider.getAlphabeticalRadioShowList(vendor, channelId, pageSize), callback);
    }


    public LiveData<IlResponse<ShowListResult>> getAlphabeticalRadioShowListLiveData(@NonNull Vendor vendor, String channelId, @Nullable Integer pageSize) {
        return ilDataProvider.getAlphabeticalRadioShowListLiveData(vendor, channelId, pageSize);
    }


    public Cancellable getAlphabeticalRadioShowListUnlimited(@NonNull Vendor vendor, String channelId, @NonNull IlDataProviderCallback<ShowListResult> callback) {
        return enqueue(ilDataProvider.getAlphabeticalRadioShowListUnlimited(vendor, channelId), callback);

    }


    public LiveData<IlResponse<ShowListResult>> getAlphabeticalRadioShowListUnlimitedLiveData(@NonNull Vendor vendor, String channelId) {
        return ilDataProvider.getAlphabeticalRadioShowListUnlimitedLiveData(vendor, channelId);
    }


    public Cancellable getAudioByDate(@NonNull Vendor vendor, Calendar date, String channelId, Integer pageSize, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getAudioByDate(vendor, date, channelId, pageSize), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getAudioByDateLiveData(@NonNull Vendor vendor, Calendar date, String channelId, Integer pageSize) {
        return ilDataProvider.getAudioByDateLiveData(vendor, date, channelId, pageSize);
    }


    public Cancellable getSongList(@NonNull Vendor vendor, String channelId, Integer pageSize, @NonNull IlDataProviderCallback<SongListResult> callback) {
        return enqueue(ilDataProvider.getSongList(vendor, channelId, pageSize), callback);
    }


    public LiveData<IlResponse<SongListResult>> getSongListLiveData(@NonNull Vendor vendor, String channelId, Integer pageSize) {
        return ilDataProvider.getSongListLiveData(vendor, channelId, pageSize);
    }


    public Cancellable getSongListUnlimited(@NonNull Vendor vendor, String channelId, @NonNull IlDataProviderCallback<SongListResult> callback) {
        return enqueue(ilDataProvider.getSongListUnlimited(vendor, channelId), callback);
    }


    public LiveData<IlResponse<SongListResult>> getSongListUnlimitedLiveData(@NonNull Vendor vendor, String channelId) {
        return ilDataProvider.getCurrentSongListUnilimitedLiveData(vendor, channelId);
    }

    //endregion radio

    //region search

    public Cancellable getMostClickedSearchedShow(@NonNull Vendor vendor, @NonNull IlDataProviderCallback<ShowListResult> callback) {
        return enqueue(ilDataProvider.getMostClickedSearchedShow(vendor), callback);
    }


    public LiveData<IlResponse<ShowListResult>> getMostClickedSearchedShowLiveData(@NonNull Vendor vendor) {
        return ilDataProvider.getMostClickedSearchedShowLiveData(vendor);
    }


    public Cancellable searchMedia(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.MediaParams params, @NonNull IlDataProviderCallback<SearchResultMediaList> callback) {
        return enqueue(ilDataProvider.searchMedia(vendor, searchTerm, params), callback);
    }


    public LiveData<IlResponse<SearchResultMediaList>> searchMediaLiveData(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.MediaParams params) {
        return ilDataProvider.searchMediaLiveData(vendor, searchTerm, params);
    }


    public Cancellable searchShow(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.ShowParams params, @NonNull IlDataProviderCallback<SearchResultShowList> callback) {
        return enqueue(ilDataProvider.searchShow(vendor, searchTerm, params), callback);
    }


    public LiveData<IlResponse<SearchResultShowList>> searchShowLiveData(@NonNull Vendor vendor, String searchTerm, @NonNull SearchParams.ShowParams params) {
        return ilDataProvider.searchShowLiveData(vendor, searchTerm, params);
    }
    //endregion search

    //region buSpecific

    public Cancellable programList(@NonNull Vendor vendor, @NonNull Transmission transmission, String channelId, Integer pageSize, @Nullable Date minEndTime, @Nullable Date maxStartTime, @Nullable String liveStreamId, IlDataProviderCallback<ProgramCompositionListResult> callback) {
        return enqueue(ilDataProvider.programList(vendor, transmission, channelId, pageSize, minEndTime, maxStartTime, liveStreamId), callback);
    }


    public LiveData<IlResponse<ProgramCompositionListResult>> programListLiveData(@NonNull Vendor vendor, @NonNull Transmission transmission, String channelId, Integer pageSize, @Nullable Date minEndTime, @Nullable Date maxStartTime, @Nullable String liveStreamId) {
        return ilDataProvider.programListLiveData(vendor, transmission, channelId, pageSize, minEndTime, maxStartTime, liveStreamId);
    }


    public Cancellable programGuide(@NonNull Vendor vendor, @NonNull Date date, IlDataProviderCallback<ProgramGuide> callback) {
        return enqueue(ilDataProvider.programGuide(vendor, date), callback);
    }


    public Cancellable programGuide(@NonNull Vendor vendor, @NonNull Date date, boolean reduced, IlDataProviderCallback<ProgramGuide> callback) {
        return enqueue(ilDataProvider.programGuide(vendor, date, reduced), callback);
    }


    public LiveData<IlResponse<ProgramGuide>> programGuideLiveData(@NonNull Vendor vendor, @NonNull Date date) {
        return ilDataProvider.programGuideLiveData(vendor, date);
    }


    public LiveData<IlResponse<ProgramGuide>> programGuideLiveData(@NonNull Vendor vendor, @NonNull Date date, boolean reduced) {
        return ilDataProvider.programGuideLiveData(vendor, date, reduced);
    }


    public Cancellable programGuideNonSrg(@NonNull Vendor vendor, @NonNull Date date, IlDataProviderCallback<ProgramGuide> callback) {
        return enqueue(ilDataProvider.programGuideNonSrg(vendor, date), callback);
    }


    public Cancellable programGuideNonSrg(@NonNull Vendor vendor, @NonNull Date date, boolean reduced, IlDataProviderCallback<ProgramGuide> callback) {
        return enqueue(ilDataProvider.programGuideNonSrg(vendor, date, reduced), callback);
    }


    public LiveData<IlResponse<ProgramGuide>> programGuideNonSrgLiveData(@NonNull Vendor vendor, @NonNull Date date) {
        return ilDataProvider.programGuideNonSrgLiveData(vendor, date);
    }


    public LiveData<IlResponse<ProgramGuide>> programGuideNonSrgLiveData(@NonNull Vendor vendor, @NonNull Date date, boolean reduced) {
        return ilDataProvider.programGuideNonSrgLiveData(vendor, date, reduced);
    }

    //endregion buSpecific

    //region nextUrls

    public Cancellable getMediaListNextUrl(String url, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getMediaListNextUrl(url), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getMediaListNextUrlLiveData(String url) {
        return ilDataProvider.getMediaListNextUrlLiveData(url);
    }


    public Cancellable getEpisodeCompositionNextUrl(String url, @NonNull IlDataProviderCallback<EpisodeCompositionListResult> callback) {
        return enqueue(ilDataProvider.getEpisodeCompositionNextUrl(url), callback);
    }


    public LiveData<IlResponse<EpisodeCompositionListResult>> getEpisodeCompositionNextUrlLiveData(String url) {
        return ilDataProvider.getEpisodeCompositionNextUrlLiveData(url);
    }


    public Cancellable getShowListNextUrl(String url, @NonNull IlDataProviderCallback<ShowListResult> callback) {
        return enqueue(ilDataProvider.getShowListNextUrl(url), callback);
    }


    public LiveData<IlResponse<ShowListResult>> getShowListNextUrlLiveData(String url) {
        return ilDataProvider.getShowListNextUrlLiveData(url);
    }


    public Cancellable getSongListNextUrl(String url, @NonNull IlDataProviderCallback<SongListResult> callback) {
        return enqueue(ilDataProvider.getSongListNextUrl(url), callback);
    }


    public LiveData<IlResponse<SongListResult>> getSongListNextUrlLiveData(String url) {
        return ilDataProvider.getSongListNextUrlLiveData(url);
    }


    public Cancellable searchMedia(String url, @NonNull IlDataProviderCallback<SearchResultMediaList> callback) {
        return enqueue(ilDataProvider.searchMediaNextUrl(url), callback);
    }


    public LiveData<IlResponse<SearchResultMediaList>> searchMediaLiveData(String url) {
        return ilDataProvider.searchMediaNextUrlLiveData(url);
    }


    public Cancellable searchShow(String url, @NonNull IlDataProviderCallback<SearchResultShowList> callback) {
        return enqueue(ilDataProvider.searchShowNextUrl(url), callback);
    }


    public LiveData<IlResponse<SearchResultShowList>> searchShowLiveData(String url) {
        return ilDataProvider.searchShowNextUrlLiveData(url);
    }
    //endregion nextUrls

    //region statistics

    public Cancellable mediaClicked(@NonNull Chapter chapter, @Nullable String userId, @NonNull IlDataProviderCallback<Object> callback) {
        Call<Object> call = ilDataProvider.postMediaClicked(chapter, userId);
        if (call != null) {
            call.enqueue(callback);
            return call::cancel;
        }
        return () -> {
            // Nothing to do
        };
    }


    public Cancellable mediaLiked(@NonNull Chapter chapter, @NonNull String userId, @NonNull IlDataProviderCallback<Object> callback) {
        Call<Object> call = ilDataProvider.postMediaLiked(chapter, userId);
        if (call != null) {
            call.enqueue(callback);
            return call::cancel;
        }
        return () -> {
            // Nothing to do
        };
    }

    //endregion statistics

    //region pac

    public Cancellable getLandingPageByProduct(@NonNull Vendor vendor, @NonNull String product, boolean isPublished, @NonNull IlDataProviderCallback<Page> callback) {
        return enqueue(ilDataProvider.getLandingPageByProduct(vendor, product, isPublished), callback);
    }


    public LiveData<IlResponse<Page>> getLandingPageByProductLiveData(@NonNull Vendor vendor, @NonNull String product, boolean isPublished) {
        return ilDataProvider.getLandingPageByProductLiveData(vendor, product, isPublished);
    }


    public Cancellable getPage(@NonNull Vendor vendor, @NonNull String pageId, boolean isPublished, @NonNull IlDataProviderCallback<Page> callback) {
        return enqueue(ilDataProvider.getPage(vendor, pageId, isPublished), callback);
    }


    public LiveData<IlResponse<Page>> getPageLiveData(@NonNull Vendor vendor, @NonNull String pageId, boolean isPublished) {
        return ilDataProvider.getPageLiveData(vendor, pageId, isPublished);
    }


    public Cancellable getSection(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished, @NonNull IlDataProviderCallback<Section> callback) {
        return enqueue(ilDataProvider.getSection(vendor, sectionId, isPublished), callback);
    }


    public LiveData<IlResponse<Section>> getSectionLiveData(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished) {
        return ilDataProvider.getSectionLiveData(vendor, sectionId, isPublished);
    }


    public Cancellable getPageForTopic(@NonNull Vendor vendor, @NonNull String topicUrn, boolean isPublished, @NonNull IlDataProviderCallback<Page> callback) {
        return enqueue(ilDataProvider.getPageForTopic(vendor, topicUrn, isPublished), callback);
    }


    public LiveData<IlResponse<Page>> getPageForTopicLiveData(@NonNull Vendor vendor, @NonNull String topicUrn, boolean isPublished) {
        return ilDataProvider.getPageForTopicLiveData(vendor, topicUrn, isPublished);
    }


    public Cancellable getMediaListForMediaSection(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished, @NonNull IlDataProviderCallback<MediaListResult> callback) {
        return enqueue(ilDataProvider.getMediaListForMediaSection(vendor, sectionId, isPublished), callback);
    }


    public LiveData<IlResponse<MediaListResult>> getMediaListForMediaSectionLiveData(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished) {
        return ilDataProvider.getMediaListForMediaSectionLiveData(vendor, sectionId, isPublished);
    }


    public Cancellable getMediaListForMediaSectionWithShow(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished, @NonNull IlDataProviderCallback<MediaListWithShowResult> callback) {
        return enqueue(ilDataProvider.getMediaListForMediaSectionWithShow(vendor, sectionId, isPublished), callback);
    }


    public LiveData<IlResponse<MediaListWithShowResult>> getMediaListForMediaSectionWithShowLiveData(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished) {
        return ilDataProvider.getMediaListForMediaSectionWithShowLiveData(vendor, sectionId, isPublished);
    }


    public Cancellable getShowListForShowSection(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished, @NonNull IlDataProviderCallback<ShowListResult> callback) {
        return enqueue(ilDataProvider.getShowListForShowSection(vendor, sectionId, isPublished), callback);
    }


    public LiveData<IlResponse<ShowListResult>> getShowListForShowSectionLiveData(@NonNull Vendor vendor, @NonNull String sectionId, boolean isPublished) {
        return ilDataProvider.getShowListForShowSectionLiveData(vendor, sectionId, isPublished);
    }
    //endregion pac

    public LiveData<IlResponse<Topic>> findTopic(@NonNull Vendor vendor, @NonNull String topicUrn) {
        return Transformations.map(ilDataProvider.getTopicsLiveData(vendor), input -> {
            if (input.isSuccessful() && input.getBody() != null) {
                for (Topic topic : input.getBody().getList()) {
                    if (TextUtils.equals(topic.getUrn(), topicUrn)) {
                        return new IlResponse<>(topic, 200, null, IlResponse.Status.SUCCESS);
                    }
                }
            }
            return new IlResponse<>(null, input.getResponseCode(), input.getThrowable(), IlResponse.Status.ERROR);
        });
    }
}
