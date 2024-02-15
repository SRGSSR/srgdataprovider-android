package ch.srg.dataProvider.integrationlayer.request.image

import ch.srg.dataProvider.integrationlayer.data.ImageUrlDecorator
import ch.srg.dataProvider.integrationlayer.request.IlHost

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */

/**
 * Default image url decorator
 *
 * For specific RTS image url, the old [ScaleWidthImageUrlDecorator] is used, but it should be fixed sooner or later.
 *
 * @param ilHost The [IlHost] to use with [ilHostImageUrlDecorator].
 */
class DefaultImageUrlDecorator(ilHost: IlHost = IlHost.PROD) : ImageUrlDecorator {
    private val ilHostImageUrlDecorator = IlHostImageUrlDecorator(ilHost)

    override fun decorate(sourceUrl: String, widthPixels: Int): String {
        // FIXME https://github.com/SRGSSR/srgdataprovider-apple/issues/47 once RTS image service is well connected to Il Play image service.
        return if (sourceUrl.contains("rts.ch") && sourceUrl.contains(".image")) {
            ScaleWidthImageUrlDecorator.decorate(sourceUrl, widthPixels)
        } else {
            ilHostImageUrlDecorator.decorate(sourceUrl, widthPixels)
        }
    }
}
