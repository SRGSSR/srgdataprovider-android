package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
interface SRGLanguage {
    val locale: String
    val language: String?
}

@Serializable
data class Language(override val locale: String, override val language: String? = null) : SRGLanguage
