package ch.srg.dataProvider.integrationlayer;

import android.net.Uri;

import ch.srg.dataProvider.integrationlayer.request.IlHost;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public class SRGUrlFactory {
    private final Uri hostUri;
    private final Uri ilBaseUri;

    public SRGUrlFactory(IlHost ilHost) {
        hostUri = ilHost.getHostUri();
        ilBaseUri = Uri.parse(ilHost.getHostUri() + "/integrationlayer/");
    }

    public Uri getHostUri() {
        return hostUri;
    }

    public Uri getIlBaseUrl() {
        return ilBaseUri;
    }

}
