package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class ProgramGuide(
    override val next: String? = null,
    @SerialName("programGuide")
    override val data: List<ProgramCompositionListResult>?
) : ListResult<ProgramCompositionListResult>() {

    fun findChannelList(): List<Channel> {
        return map { it.channel }
    }
}
