package ch.srg.dataProvider.integrationlayer.legacy.requests;

import androidx.lifecycle.LiveData;

import ch.srg.dataProvider.integrationlayer.data.MediaComposition;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public interface LegacyIlMediaCompositionService {

    @GET("2.1/mediaComposition/byUrn/{urn}")
    Call<MediaComposition> getMediaComposition(@Path("urn") String urn, @Query("onlyChapters") boolean onlyChapters);

    @GET("2.1/mediaComposition/byUrn/{urn}")
    LiveData<IlResponse<MediaComposition>> getMediaCompositionLiveData(@Path("urn") String urn, @Query("onlyChapters") boolean onlyChapters);
}
