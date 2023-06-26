package ch.srg.dataProvider.integrationlayer.request.parameters

import ch.srg.dataProvider.integrationlayer.data.remote.MediaType

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class IlMediaType(mediaType: MediaType) : IlParam(mediaType.toString().lowercase())
