package ch.srg.dataProvider.integrationlayer.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Program(
    override val title: String,
    val startTime: Date,
    val endTime: Date,
    override val lead: String? = null,
    override val description: String? = null,
    override val imageUrl: ImageUrl,
    override val imageFocalPoint: FocalPoint? = null,
    override val imageTitle: String? = null,
    override val imageCopyright: String? = null,
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
    @SerializedName("creditList")
    val crewMemberList: List<CrewMember>? = null,
    val subProgramList: List<Program>? = null,
    val subtitlesAvailable: Boolean = false,
    @SerializedName("hasTwoLanguages")
    val hasAlternateAudio: Boolean = false,
    val hasSignLanguage: Boolean = false,
    @SerializedName("hasVisualDescription")
    val hasAudioDescription: Boolean = false,
    val isFollowUp: Boolean = false,
    val isDolbyDigital: Boolean = false,
    @SerializedName("isRepetition")
    val isRebroadcast: Boolean = false,
    @SerializedName("repetitionDescription")
    val rebroadcastDescription: String? = null,
    val channelTitle: String? = null,
    val channelUrn: String? = null
) : SRGImageMetadata, SRGMetadata {

    fun isDateInProgramTime(date: Date): Boolean {
        return date.after(startTime) && date.before(endTime)
    }
}

data class CrewMember(var realName: String, var role: String? = null, var name: String? = null)
