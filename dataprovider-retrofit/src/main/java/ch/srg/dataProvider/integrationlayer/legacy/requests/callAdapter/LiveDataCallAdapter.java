package ch.srg.dataProvider.integrationlayer.legacy.requests.callAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import ch.srg.dataProvider.integrationlayer.legacy.requests.IlResponse;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 * <p>
 * https://gist.github.com/AkshayChordiya/15cfe7ca1842d6b959e77c04a073a98f
 */
public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<IlResponse<T>>> {
    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    /**
     * Retrofit Call is called when the number of active observers change to 1 from 0.
     */
    @Override
    public LiveData<IlResponse<T>> adapt(@NonNull Call<T> call) {
        return new LiveData<IlResponse<T>>() {
            private AtomicBoolean isStarted = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (isStarted.compareAndSet(false, true)) {
                    postValue(new IlResponse<>(getValue() != null ? getValue().body : null, 0, null, IlResponse.Status.LOADING));
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                            postValue(IlResponse.create(response));
                        }

                        @Override
                        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                            postValue(IlResponse.create(t));
                        }
                    });
                }
            }
        };
    }
}
