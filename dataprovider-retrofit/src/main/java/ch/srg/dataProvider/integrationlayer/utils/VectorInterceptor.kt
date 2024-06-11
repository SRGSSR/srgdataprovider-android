package ch.srg.dataProvider.integrationlayer.utils

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Vector interceptor
 *
 * Add to each http request a query parameter "vector" with value [vector].
 *
 * @property vector the query parameters value.
 */
class VectorInterceptor(private val vector: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        if (originalHttpUrl.queryParameter("vector") != null) {
            return chain.proceed(originalRequest)
        }

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("vector", vector)
            .build()

        // Request customization: add request headers
        val requestBuilder = originalRequest.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
