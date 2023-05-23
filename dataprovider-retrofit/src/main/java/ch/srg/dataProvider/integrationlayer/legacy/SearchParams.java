package ch.srg.dataProvider.integrationlayer.legacy;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.srg.dataProvider.integrationlayer.data.MediaType;
import ch.srg.dataProvider.integrationlayer.data.Quality;

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
public final class SearchParams {

    private SearchParams() {

    }

    public enum Operator {
        AND,
        OR;

        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum SortBy {
        DEFAULT,
        DATE;

        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum SortDir {
        DESC,
        ASC;

        public String toString() {
            return name().toLowerCase();
        }
    }

    public static class MediaParams extends Params {
        @Nullable
        public List<String> topicUrns;
        @Nullable
        public List<String> showUrns;
        @Nullable
        public Boolean subtitlesAvailable;
        @Nullable
        public Boolean downloadAvailable;
        @Nullable
        public Boolean playableAbroad;
        @Nullable
        public Quality quality;
        @Nullable
        public Integer durationFromInMinutes;
        @Nullable
        public Integer durationToInMinutes;
        @Nullable
        public String publishedDateFrom;
        @Nullable
        public String publishedDateTo;
        @Nullable
        public Boolean includeAggregations;
        @Nullable
        public Boolean includeSuggestions;
        @Nullable
        public Boolean enableFuzzySearch;
        @Nullable
        public Operator operator;
        @Nullable
        public SortBy sortBy;
        @Nullable
        public SortDir sortDir;

        public MediaParams() {
            super();
            // Nothing all parameters a null
        }

        public MediaParams(@NonNull MediaParams source) {
            super(source);
            if (source.topicUrns != null) {
                topicUrns = new ArrayList<>(source.topicUrns);
            } else {
                topicUrns = null;
            }
            if (source.showUrns != null) {
                showUrns = new ArrayList<>(source.showUrns);
            } else {
                showUrns = null;
            }
            subtitlesAvailable = source.subtitlesAvailable;
            downloadAvailable = source.downloadAvailable;
            playableAbroad = source.playableAbroad;
            quality = source.quality;
            durationFromInMinutes = source.durationFromInMinutes;
            durationToInMinutes = source.durationToInMinutes;
            publishedDateFrom = source.publishedDateFrom;
            publishedDateTo = source.publishedDateTo;
            includeAggregations = source.includeAggregations;
            includeSuggestions = source.includeSuggestions;
            enableFuzzySearch = source.enableFuzzySearch;
            operator = source.operator;
            sortBy = source.sortBy;
            sortDir = source.sortDir;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MediaParams that = (MediaParams) o;

            if (topicUrns != null ? !topicUrns.equals(that.topicUrns) : that.topicUrns != null)
                return false;
            if (showUrns != null ? !showUrns.equals(that.showUrns) : that.showUrns != null)
                return false;
            if (subtitlesAvailable != null ? !subtitlesAvailable.equals(that.subtitlesAvailable) : that.subtitlesAvailable != null)
                return false;
            if (downloadAvailable != null ? !downloadAvailable.equals(that.downloadAvailable) : that.downloadAvailable != null)
                return false;
            if (playableAbroad != null ? !playableAbroad.equals(that.playableAbroad) : that.playableAbroad != null)
                return false;
            if (quality != that.quality) return false;
            if (durationFromInMinutes != null ? !durationFromInMinutes.equals(that.durationFromInMinutes) : that.durationFromInMinutes != null)
                return false;
            if (durationToInMinutes != null ? !durationToInMinutes.equals(that.durationToInMinutes) : that.durationToInMinutes != null)
                return false;
            if (publishedDateFrom != null ? !publishedDateFrom.equals(that.publishedDateFrom) : that.publishedDateFrom != null)
                return false;
            if (publishedDateTo != null ? !publishedDateTo.equals(that.publishedDateTo) : that.publishedDateTo != null)
                return false;
            if (includeAggregations != null ? !includeAggregations.equals(that.includeAggregations) : that.includeAggregations != null)
                return false;
            if (includeSuggestions != null ? !includeSuggestions.equals(that.includeSuggestions) : that.includeSuggestions != null)
                return false;
            if (enableFuzzySearch != null ? !enableFuzzySearch.equals(that.enableFuzzySearch) : that.enableFuzzySearch != null)
                return false;
            if (operator != that.operator) return false;
            if (sortBy != that.sortBy) return false;
            return sortDir == that.sortDir;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (topicUrns != null ? topicUrns.hashCode() : 0);
            result = 31 * result + (showUrns != null ? showUrns.hashCode() : 0);
            result = 31 * result + (subtitlesAvailable != null ? subtitlesAvailable.hashCode() : 0);
            result = 31 * result + (downloadAvailable != null ? downloadAvailable.hashCode() : 0);
            result = 31 * result + (playableAbroad != null ? playableAbroad.hashCode() : 0);
            result = 31 * result + (quality != null ? quality.hashCode() : 0);
            result = 31 * result + (durationFromInMinutes != null ? durationFromInMinutes.hashCode() : 0);
            result = 31 * result + (durationToInMinutes != null ? durationToInMinutes.hashCode() : 0);
            result = 31 * result + (publishedDateFrom != null ? publishedDateFrom.hashCode() : 0);
            result = 31 * result + (publishedDateTo != null ? publishedDateTo.hashCode() : 0);
            result = 31 * result + (includeAggregations != null ? includeAggregations.hashCode() : 0);
            result = 31 * result + (includeSuggestions != null ? includeSuggestions.hashCode() : 0);
            result = 31 * result + (enableFuzzySearch != null ? enableFuzzySearch.hashCode() : 0);
            result = 31 * result + (operator != null ? operator.hashCode() : 0);
            result = 31 * result + (sortBy != null ? sortBy.hashCode() : 0);
            result = 31 * result + (sortDir != null ? sortDir.hashCode() : 0);
            return result;
        }

        @NonNull
        public Map<String, String> toQueryMap() {
            HashMap<String, String> result = new HashMap<>();
            put(result, "topicUrns", topicUrns);
            put(result, "showUrns", showUrns);
            put(result, "mediaType", mediaType);
            put(result, "subtitlesAvailable", subtitlesAvailable);
            put(result, "downloadAvailable", downloadAvailable);
            put(result, "playableAbroad", playableAbroad);
            put(result, "quality", quality);
            put(result, "durationFromInMinutes", durationFromInMinutes);
            put(result, "durationToInMinutes", durationToInMinutes);
            put(result, "publishedDateFrom", publishedDateFrom);
            put(result, "publishedDateTo", publishedDateTo);
            put(result, "pageSize", pageSize);
            put(result, "includeAggregations", includeAggregations);
            put(result, "includeSuggestions", includeSuggestions);
            put(result, "enableFuzzySearch", enableFuzzySearch);
            put(result, "operator", operator);
            put(result, "sortBy", sortBy);
            put(result, "sortDir", sortDir);
            return result;
        }
    }

    public static class ShowParams extends Params {

        public ShowParams() {
            super();
        }

        public ShowParams(@NonNull ShowParams source) {
            super(source);
        }

        @NonNull
        public Map<String, String> toQueryMap() {
            HashMap<String, String> result = new HashMap<>();
            put(result, "mediaType", mediaType);
            put(result, "pageSize", pageSize);
            return result;
        }
    }

    private static class Params {
        @Nullable
        public MediaType mediaType;
        @Nullable
        public Integer pageSize;

        public Params() {
            mediaType = null;
            pageSize = null;
        }

        public Params(@NonNull Params source) {
            mediaType = source.mediaType;
            pageSize = source.pageSize;
        }

        protected static void put(HashMap<String, String> result, String key, Object value) {
            if (value != null) {
                result.put(key, value.toString());
            }
        }

        protected static void put(HashMap<String, String> result, String key, List<String> values) {
            if (values != null) {
                result.put(key, TextUtils.join(",", values));
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Params params = (Params) o;

            if (mediaType != params.mediaType) return false;
            return pageSize != null ? pageSize.equals(params.pageSize) : params.pageSize == null;
        }

        @Override
        public int hashCode() {
            int result = mediaType != null ? mediaType.hashCode() : 0;
            result = 31 * result + (pageSize != null ? pageSize.hashCode() : 0);
            return result;
        }
    }
}
