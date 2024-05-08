package ch.srg.dataProvider.integrationlayer.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Link(
    val targetType: String,
    val target: String,
)
