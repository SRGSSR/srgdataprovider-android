package ch.srg.dataProvider.integrationlayer.data

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
interface SRGSubDivision : SRGMediaMetadata {
    val displayable: Boolean
    val fullLengthUrn: String?
    val subtitleList: List<Subtitle>?
    val comScoreAnalyticsLabels: HashMap<String, String>?
    val analyticsLabels: HashMap<String, String>?
    val eventData: String?
}
