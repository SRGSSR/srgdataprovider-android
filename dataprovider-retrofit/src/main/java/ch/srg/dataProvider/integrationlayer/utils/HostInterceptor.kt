package ch.srg.dataProvider.integrationlayer.utils

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds a query parameter "forceSAM=true" to each SAM HTTP request.
 * See [IlHost] for more details.
 */
class HostInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        if (originalHttpUrl.queryParameter(FORCE_SAM) != null || !originalHttpUrl.pathSegments.contains("sam")) {
            return chain.proceed(originalRequest)
        }

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(FORCE_SAM, "true")
            .build()

        // Request customization: add request headers
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private const val FORCE_SAM = "forceSAM"
    }
}
