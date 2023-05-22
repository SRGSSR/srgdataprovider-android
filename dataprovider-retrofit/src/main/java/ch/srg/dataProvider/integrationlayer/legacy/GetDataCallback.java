package ch.srg.dataProvider.integrationlayer.legacy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public interface GetDataCallback<T> {
    void onDataLoaded(@NonNull Call<T> call, @NonNull Response<T> response, @NonNull T data);

    void onDataNotAvailable(@NonNull Call<T> call, @Nullable Response<T> response, @NonNull Throwable throwable);

    void onDataCallCancelled(@NonNull Call<T> call);
}
