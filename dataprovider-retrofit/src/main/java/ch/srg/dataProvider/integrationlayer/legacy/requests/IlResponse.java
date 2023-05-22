package ch.srg.dataProvider.integrationlayer.legacy.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.Response;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class IlResponse<T> {

    public enum Status {
        IDLE,
        LOADING,
        SUCCESS,
        ERROR
    }

    @Nullable
    public final T body;
    public final int responseCode;
    @Nullable
    public final Throwable throwable;
    @Nullable
    public Status status;

    public IlResponse(@Nullable T body, int responseCode, @Nullable Throwable throwable, @Nullable Status status) {
        this.body = body;
        this.responseCode = responseCode;
        this.throwable = throwable;
        this.status = status;
    }

    /**
     * Returns true if the responseCode is in [200..300]
     */
    public boolean isSuccessful() {
        return responseCode >= 200 && responseCode < 300;
    }

    @Nullable
    public T getBody() {
        return body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }

    @NonNull
    public static <T> IlResponse<T> create(@NonNull Response<T> response) {
        if (response.isSuccessful()) {
            return new IlResponse<>(response.body(), response.code(), null, Status.SUCCESS);
        } else {
            return new IlResponse<>(null, response.code(), null, Status.ERROR);
        }
    }

    @NonNull
    public static <T> IlResponse<T> create(@NonNull Throwable throwable) {
        return new IlResponse<>(null, 500, throwable, Status.ERROR);
    }
}
