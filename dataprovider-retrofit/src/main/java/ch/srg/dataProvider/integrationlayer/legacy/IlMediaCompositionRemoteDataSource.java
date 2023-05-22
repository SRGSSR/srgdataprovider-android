package ch.srg.dataProvider.integrationlayer.legacy;

import android.util.Log;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ch.srg.dataProvider.integrationlayer.data.MediaComposition;
import ch.srg.dataProvider.integrationlayer.legacy.requests.LegacyIlMediaCompositionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class IlMediaCompositionRemoteDataSource {
    private static final String TAG = "IlMCRemoteDataSource";

    @NonNull
    private final LegacyIlMediaCompositionService ilMediaCompositionService;

    @Inject
    public IlMediaCompositionRemoteDataSource(@NonNull LegacyIlMediaCompositionService ilMediaCompositionService) {
        this.ilMediaCompositionService = ilMediaCompositionService;
    }

    public Cancellable getMediaComposition(@NonNull String urn, @NonNull final GetMediaCompositionCallback callback) {
        return getMediaComposition(urn, false, callback);
    }

    public Cancellable getMediaComposition(@NonNull String urn, boolean onlyChapters, @NonNull final GetMediaCompositionCallback callback) {
        final Call<MediaComposition> call = ilMediaCompositionService.getMediaComposition(urn, onlyChapters);
        call.enqueue(new Callback<MediaComposition>() {
            @Override
            public void onResponse(@NonNull Call<MediaComposition> call, @NonNull Response<MediaComposition> response) {
                MediaComposition result = response.isSuccessful() ? response.body() : null;
                if (result != null) {
                    if (result.findChapter(result.getChapterUrn()) != null) {
                        callback.onMediaCompositionLoaded(call, response, result);
                    } else {
                        Log.e(TAG, "Invalid mediacomposition " + result);
                        callback.onMediaCompositionNotAvailable(call, response, new IlMediaCompositionException("Invalid mediacomposition"));
                    }
                } else {
                    callback.onMediaCompositionNotAvailable(call, response, new IlMediaCompositionException("No body in IL response"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MediaComposition> call, Throwable t) {
                if (call.isCanceled()) {
                    callback.onMediaCompositionCallCancelled(call);
                } else {
                    callback.onMediaCompositionNotAvailable(call, null, t);
                }
            }
        });

        return call::cancel;
    }
}
