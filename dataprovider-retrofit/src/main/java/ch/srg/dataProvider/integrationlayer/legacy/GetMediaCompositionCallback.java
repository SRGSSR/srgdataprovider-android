package ch.srg.dataProvider.integrationlayer.legacy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ch.srg.dataProvider.integrationlayer.data.MediaComposition;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public interface GetMediaCompositionCallback {
    void onMediaCompositionLoaded(@NonNull Call<MediaComposition> call, @NonNull Response<MediaComposition> response, @NonNull MediaComposition mediaComposition);

    void onMediaCompositionNotAvailable(@NonNull Call<MediaComposition> call, @Nullable Response<MediaComposition> response, @NonNull Throwable throwable);

    void onMediaCompositionCallCancelled(@NonNull Call<MediaComposition> call);
}