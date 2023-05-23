package ch.srg.dataProvider.integrationlayer.data;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public final class IlHost implements Serializable {
    public static final IlHost PROD = new IlHost("PROD", "il.srgssr.ch");
    public static final IlHost TEST = new IlHost("TEST", "il-test.srgssr.ch");
    public static final IlHost STAGE = new IlHost("STAGE", "il-stage.srgssr.ch");
    public static final IlHost MMF = new IlHost("MMF", "play-mmf.herokuapp.com/android_26CE9E49-9600");
    public static final IlHost MMF_PUBLIC = new IlHost("MMF", "play-mmf.herokuapp.com");
    public static final IlHost PROD_SAM = new IlHost("PROD_SAM", "il.srgssr.ch/sam");
    public static final IlHost TEST_SAM = new IlHost("TEST_SAM", "il-test.srgssr.ch/sam");
    public static final IlHost STAGE_SAM = new IlHost("STAGE_SAM", "il-stage.srgssr.ch/sam");

    @NonNull
    private final String value;
    @NonNull
    private final String name;

    /**
     * @param name  designed name
     * @param value hostname of the integration layer url
     */
    public IlHost(@NonNull final String name, @NonNull final String value) {
        this.name = name;
        this.value = value;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Uri getHostUri() {
        return Uri.parse("https://" + value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IlHost ilHost = (IlHost) o;

        if (!value.equals(ilHost.value)) return false;
        return name.equals(ilHost.name);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
