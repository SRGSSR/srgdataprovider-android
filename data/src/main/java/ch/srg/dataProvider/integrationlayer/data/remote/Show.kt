package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Serializable
data class Show(
    override val id: String,
    override val vendor: Vendor,
    val transmission: Transmission,
    override val urn: String,
    override val title: String,
    override val imageUrl: ImageUrl,
    override val lead: String? = null,
    override val description: String? = null,
    val primaryChannelUrn: String? = null,
    val primaryChannelId: String? = null,
    override val imageFocalPoint: FocalPoint? = null,
    override val imageTitle: String? = null,
    override val imageCopyright: String? = null,
    val bannerImageUrl: ImageUrl? = null,
    val posterImageUrl: ImageUrl? = null,
    val posterImageIsFallbackUrl: Boolean = false,
    val podcastImageUrl: ImageUrl? = null,
    val podcastImageIsFallbackUrl: Boolean = false,
    val podcastSubscriptionUrl: String? = null,
    val podcastFeedSdUrl: String? = null,
    val podcastFeedHdUrl: String? = null,
    val podcastDeezerUrl: String? = null,
    val podcastSpotifyUrl: String? = null,
    val timeTableUrl: String? = null,
    val homepageUrl: String? = null,
    val numberOfEpisodes: Int? = null,
    val topicList: List<Topic>? = null,
    val broadcastInformation: BroadCastInformation? = null,
    val audioDescriptionAvailable: Boolean? = null,
    val subtitlesAvailable: Boolean? = null,
    val multiAudioLanguagesAvailable: Boolean? = null,
    val availableAudioLanguageList: List<Language>? = null,
    val availableSubtitleLanguageList: List<Language>? = null
) : SRGIdentifierMetadata, SRGMetadata, SRGImageMetadata
