package ch.srg.dataProvider.integrationlayer.legacy;

import java.util.List;

import ch.srg.dataProvider.integrationlayer.data.Show;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public interface GetShowListPartialCallback {
    /**
     * This call might be called multiple times during download in case multiple network requests
     * are necessary.
     * <p>
     * Clients must expect the following sequences:
     * <pre>
     *     request { A, B, C }
     *
     *     Callbacks:
     *        onShowListLoaded({ A }, true);
     *        onShowListLoaded({ A, B }, true);
     *        onShowListLoaded({ A, B, C }, false);
     * </pre>
     * <p>
     * or in case of error between network connections:
     * <pre>
     *     request { A, B, C }
     *
     *     Callbacks:
     *        onShowListLoaded({ A }, true);
     *        onShowListLoaded({ A, B }, true);
     *        onShowListNotAvailable();
     * </pre>
     *
     * @param showList   all shows received up to now
     * @param incomplete true if more show can be received.
     */
    void onShowListLoaded(List<Show> showList, boolean incomplete);

    void onShowListNotAvailable();

    void onShowListCallCancelled();
}
