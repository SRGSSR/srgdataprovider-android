package ch.srg.dataProvider.integrationlayer.data.remote

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 *
 * TODO check if we convert it has a Json object if it is serialized when using with retrofit
 */
class SearchParams {
    enum class Operator {
        AND, OR;

        override fun toString(): String {
            return name.lowercase()
        }
    }

    enum class SortBy {
        DEFAULT, DATE;

        override fun toString(): String {
            return name.lowercase()
        }
    }

    enum class SortDir {
        DESC, ASC;

        override fun toString(): String {
            return name.lowercase()
        }
    }

    data class MediaParams(
        override var mediaType: MediaType? = null,
        var topicUrns: List<String>? = null,
        var showUrns: List<String>? = null,
        var subtitlesAvailable: Boolean? = null,
        var downloadAvailable: Boolean? = null,
        var playableAbroad: Boolean? = null,
        var quality: Quality? = null,
        var durationFromInMinutes: Int? = null,
        var durationToInMinutes: Int? = null,
        var publishedDateFrom: String? = null,
        var publishedDateTo: String? = null,
        var includeAggregations: Boolean? = null,
        var includeSuggestions: Boolean? = null,
        var enableFuzzySearch: Boolean? = null,
        var operator: Operator? = null,
        var sortBy: SortBy? = null,
        var sortDir: SortDir? = null,
    ) : Params {
        /**
         * QueryMap for retrofit
         */
        fun toQueryMap(): Map<String, String> {
            val result = HashMap<String, String>()
            put(result, "topicUrns", topicUrns)
            put(result, "showUrns", showUrns)
            put(result, "mediaType", mediaType)
            put(result, "subtitlesAvailable", subtitlesAvailable)
            put(result, "downloadAvailable", downloadAvailable)
            put(result, "playableAbroad", playableAbroad)
            put(result, "quality", quality)
            put(result, "durationFromInMinutes", durationFromInMinutes)
            put(result, "durationToInMinutes", durationToInMinutes)
            put(result, "publishedDateFrom", publishedDateFrom)
            put(result, "publishedDateTo", publishedDateTo)
            put(result, "includeAggregations", includeAggregations)
            put(result, "includeSuggestions", includeSuggestions)
            put(result, "enableFuzzySearch", enableFuzzySearch)
            put(result, "operator", operator)
            put(result, "sortBy", sortBy)
            put(result, "sortDir", sortDir)
            return result
        }
    }

    data class ShowParams(override var mediaType: MediaType? = null) : Params {
        fun toQueryMap(): Map<String, String> {
            val result = HashMap<String, String>()
            put(result, "mediaType", mediaType)
            return result
        }
    }

    private sealed interface Params {
        var mediaType: MediaType?
    }

    companion object {
        private fun put(result: HashMap<String, String>, key: String, value: Any?) {
            if (value != null) {
                result[key] = value.toString()
            }
        }

        private fun put(result: HashMap<String, String>, key: String, values: List<String?>?) {
            if (values != null) {
                result[key] = values.joinToString(",")
            }
        }
    }
}
