package ch.srg.dataProvider.integrationlayer.data;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public final class ImageUrl implements Serializable {
    @NonNull
    private final String url;

    public ImageUrl(@NonNull String url) {
        this.url = url;
    }

    public IlImage getIlImage(IlImage.Scaling scaling) {
        return new IlImage(url, scaling);
    }

    public IlImage getIlImage() {
        return getIlImage(IlImage.Scaling.Default);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageUrl imageUrl = (ImageUrl) o;
        return url.equals(imageUrl.url);
    }

    /**
     * Only for internal use!
     *
     * @return the undecorated url
     */
    @NonNull
    public String getRawUrl() {
        return url;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return url;
    }
}
