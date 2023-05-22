package ch.srg.dataProvider.integrationlayer.data

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
interface SRGLanguage {
    val locale: String
    val language: String?
}

data class Language(override val locale: String, override val language: String? = null) : SRGLanguage