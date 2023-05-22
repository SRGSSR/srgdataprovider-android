package ch.srg.dataProvider.integrationlayer.legacy.requests;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class IlLiveDataCallback<T> implements Callback<T> {

    @NonNull
    private MutableLiveData<IlResponse<T>> resultLiveData;

    public IlLiveDataCallback(@NonNull MutableLiveData<IlResponse<T>> resultLiveData) {
        this.resultLiveData = resultLiveData;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        resultLiveData.setValue(IlResponse.create(response));
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        resultLiveData.setValue(IlResponse.create(t));
    }
}
