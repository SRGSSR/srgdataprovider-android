package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@JsonClass(generateAdapter = true)
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
    @Json(name = "creditList")
    val crewMemberList: List<CrewMember>? = null,
    val subProgramList: List<Program>? = null,
    val subtitlesAvailable: Boolean = false,
    @Json(name = "hasTwoLanguages")
    val hasAlternateAudio: Boolean = false,
    val hasSignLanguage: Boolean = false,
    @Json(name = "hasVisualDescription")
    val hasAudioDescription: Boolean = false,
    val isFollowUp: Boolean = false,
    val isDolbyDigital: Boolean = false,
    @Json(name = "isRepetition")
    val isRebroadcast: Boolean = false,
    @Json(name = "repetitionDescription")
    val rebroadcastDescription: String? = null,
    val channelTitle: String? = null,
    val channelUrn: String? = null
) : SRGImageMetadata, SRGMetadata {

    fun isDateInProgramTime(date: Date): Boolean {
        return date.after(startTime) && date.before(endTime)
    }
}

@JsonClass(generateAdapter = true)
data class CrewMember(var realName: String, var role: String? = null, var name: String? = null)
