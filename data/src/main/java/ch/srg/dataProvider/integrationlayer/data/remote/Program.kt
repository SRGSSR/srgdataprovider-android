@file:UseSerializers(DateSerializer::class)

package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.serializer.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Program(
    override val title: String,
    val startTime: Date,
    val endTime: Date,
    override val lead: String? = null,
    override val description: String? = null,
    val imageUrl: String? = null,
    val imageFocalPoint: FocalPoint? = null,
    val imageTitle: String? = null,
    val imageCopyright: String? = null,
    val url: String? = null,
    val show: Show? = null,
    val mediaUrn: String? = null,
    val genre: String? = null,
    val seasonNumber: Int? = null,
    val episodeNumber: Int? = null,
    val episodesTotal: Int? = null,
    val productionYear: Int? = null,
    val productionCountry: String? = null,
    val youthProtectionColor: YouthProtectionColor? = null,
    val subtitle: String? = null,
    val originalTitle: String? = null,
    @SerialName("creditList")
    val crewMemberList: List<CrewMember>? = null,
    val subProgramList: List<Program>? = null,
    val subtitlesAvailable: Boolean = false,
    @SerialName("hasTwoLanguages")
    val hasAlternateAudio: Boolean = false,
    val hasSignLanguage: Boolean = false,
    @SerialName("hasVisualDescription")
    val hasAudioDescription: Boolean = false,
    val isFollowUp: Boolean = false,
    val isDolbyDigital: Boolean = false,
    @SerialName("isRepetition")
    val isRebroadcast: Boolean = false,
    @SerialName("repetitionDescription")
    val rebroadcastDescription: String? = null,
    val channelTitle: String? = null,
    val channelUrn: String? = null
) : SRGMetadata {

    fun isDateInProgramTime(date: Date): Boolean {
        return date.after(startTime) && date.before(endTime)
    }
}

@Serializable
data class CrewMember(var realName: String, var role: String? = null, var name: String? = null)
