package ch.srg.dataProvider.integrationlayer.legacy;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public abstract class IlDataProviderCallback<T> implements Callback<T>, GetDataCallback<T> {
    @Override
    public final void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful() && response.body() != null) {
            onDataLoaded(call, response, response.body());
        } else {
            onDataNotAvailable(call, response, new IlDataException("empty body"));
        }
    }

    @Override
    public final void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (call.isCanceled()) {
            onDataCallCancelled(call);
        } else {
            onDataNotAvailable(call, null, t);
        }
    }
}
