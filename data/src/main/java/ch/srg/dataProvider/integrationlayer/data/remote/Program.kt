package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Program(
    override val title: String,
    val startTime: Instant,
    val endTime: Instant,
    override val lead: String? = null,
    override val description: String? = null,
    val imageUrl: ImageUrl? = null,
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

    fun isDateInProgramTime(instant: Instant): Boolean {
        return instant > startTime && instant < endTime
    }
}

@Serializable
data class CrewMember(var realName: String, var role: String? = null, var name: String? = null)
