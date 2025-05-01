package ch.srg.dataProvider.integrationlayer.request.parameters

import ch.srg.dataProvider.integrationlayer.data.remote.EventType

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class IlEventType(eventType: EventType) : IlParam(eventType.toString().lowercase())
