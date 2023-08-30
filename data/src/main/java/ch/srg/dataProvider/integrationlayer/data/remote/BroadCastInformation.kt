@file:UseSerializers(DateSerializer::class)
package ch.srg.dataProvider.integrationlayer.data.remote
import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable

data class BroadCastInformation(
    val hintText: String? = null,
    val url: String? = null,
    val startDate: Date? = null,
    val endDate: Date? = null
)
