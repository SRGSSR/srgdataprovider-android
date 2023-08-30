package ch.srg.dataProvider.integrationlayer.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * User agent interceptor
 *
 * @property userAgent The user agent to set to OkHttp.
 */
class UserAgentInterceptor(private val userAgent: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder().header("User-Agent", userAgent)
        val request = builder.build()
        return chain.proceed(request)
    }
}
