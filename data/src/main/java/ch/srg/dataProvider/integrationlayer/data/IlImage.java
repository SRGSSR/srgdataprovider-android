package ch.srg.dataProvider.integrationlayer.data;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public final class IlImage {

    public enum Size {
        w240(240),
        w320(320),
        w480(480),
        w960(960),
        w1920(1920);
        public final int value;

        Size(int size) {
            this.value = size;
        }

        public static Size getClosest(int pixels) {
            if (pixels >= w1920.value) {
                return w1920;
            }
            if (pixels <= w240.value) {
                return w240;
            }
            Size[] sizes = Size.values();
            int closestSize = 0;
            int minDist = Integer.MAX_VALUE;
            for (int i = 0; i < sizes.length; i++) {
                int dist = Math.abs(sizes[i].value - pixels);
                if (dist <= minDist) {
                    minDist = dist;
                    closestSize = i;
                }
            }
            return sizes[closestSize];
        }
    }

    @NonNull
    private final String url;

    public IlImage(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IlImage ilImage = (IlImage) o;
        return url.equals(ilImage.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
