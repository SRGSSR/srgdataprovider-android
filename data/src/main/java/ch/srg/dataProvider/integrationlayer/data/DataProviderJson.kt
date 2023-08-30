package ch.srg.dataProvider.integrationlayer.data

import kotlinx.serialization.json.Json

val DataProviderJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    isLenient = true
}
