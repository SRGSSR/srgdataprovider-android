package ch.srg.dataProvider.integrationlayer.request.parameters

import ch.srg.dataProvider.integrationlayer.data.Type

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class IlType(type: Type) : IlParam(type.toString().lowercase())
