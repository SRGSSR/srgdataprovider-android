package ch.srg.dataProvider.integrationlayer.data

import com.google.gson.annotations.SerializedName

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class ProgramGuide(
    override val next: String? = null,
    @SerializedName("programGuide")
    override val data: List<ProgramCompositionListResult>?
) : ListResult<ProgramCompositionListResult>() {

    fun findChannelList(): List<Channel> {
        return map { it.channel }
    }
}
