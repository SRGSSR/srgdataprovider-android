package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
data class ProgramGuide(
    override val next: String? = null,
    @Json(name = "programGuide")
    override val data: List<ProgramCompositionListResult>?
) : ListResult<ProgramCompositionListResult>() {

    fun findChannelList(): List<Channel> {
        return map { it.channel }
    }
}
