package ch.srg.dataProvider.integrationlayer.data.content

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Product(val name: String) {

    override fun toString(): String {
        return name;
    }

    companion object {
        val PLAY_VIDEO = Product("PLAY_VIDEO")
        val PLAY_AUDIO = Product("PLAY_AUDIO")
        val NEWS_APP = Product("NEWS_APP")
        val NEWS_VIDEO = Product("NEWS_VIDEO")
        val NEWS_AUDIO = Product("NEWS_AUDIO")
    }
}