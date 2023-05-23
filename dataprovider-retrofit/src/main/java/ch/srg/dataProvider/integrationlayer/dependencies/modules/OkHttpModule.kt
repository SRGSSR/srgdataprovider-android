package ch.srg.dataProvider.integrationlayer.dependencies.modules

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import ch.srg.dataProvider.integrationlayer.utils.UserAgentInterceptor
import ch.srg.dataProvider.integrationlayer.utils.UserAgentUtils
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import okio.BufferedSource
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Module
class OkHttpModule {
    @Provides
    @ConfiguredScope
    @IlVector
    fun getVector(context: Context): String {
        return if (isAndroidTV(context)) "TVPLAY" else "APPPLAY"
    }

    @Provides
    @ConfiguredScope
    fun providerCache(context: Context): Cache {
        return Cache(File(context.cacheDir, DEFAULT_CACHE_DIR), 2 * 1024 * 1024)
    }

    @Provides
    @ConfiguredScope
    fun provideHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        return builder.build()
    }


    @Provides
    @ConfiguredScope
    fun provideOkHttpClientBuilder(context: Context, @IlVector vector: String, cache: Cache): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.cache(cache)
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        builder.addInterceptor(logging)
        builder.addInterceptor(UserAgentInterceptor(UserAgentUtils.createUserAgent(context)))
        builder.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("vector", vector)
                .build()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                .url(url)
            val request = requestBuilder.build()
            try {
                return@Interceptor chain.proceed(request)
            } catch (io: IOException) {
                throw io
            } catch (e: Throwable) {
                // Workaround for a corrupt cache causing an application crash.
                // Also workaround for https://github.com/square/okhttp/issues/3308
                // See https://fabric.io/srgssr/android/apps/ch.srf.mobile.srfplayer/issues/5a03913e61b02d480d07dcc9?time=last-seven-days
                // Either there is a migration issue with the previous version of okhttp
                // (as okhttp 3.4 and 3.8 have the same Cache.VERSION)
                // or the Cache is buggy for another reason.
                // In either case we don't want an invalid cache to crash the app, so evict the
                // entire cache, fail this particular request, and hopefully on the next
                // retry, the user will have something working.
                Log.e("OkHttpModule", "OkHttp Fatal Error. Clearing cache", e)
                cache.evictAll()
                return@Interceptor Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(501)
                    .message("OkHttp Cache Fatal Error $e")
                    .body(EMPTY_BODY)
                    .sentRequestAtMillis(-1L)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .build()
            }
        })
        builder.readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        builder.connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        return builder
    }

    companion object {
        private const val DEFAULT_CACHE_DIR = "play_cache"
        private const val READ_TIMEOUT_SECONDS: Long = 60
        private const val CONNECT_TIMEOUT_SECONDS: Long = 60
        private val EMPTY_BODY: ResponseBody = object : ResponseBody() {
            override fun contentType(): MediaType? {
                return null
            }

            override fun contentLength(): Long {
                return 0
            }

            override fun source(): BufferedSource {
                return Buffer()
            }
        }

        private fun isAndroidTV(context: Context): Boolean {
            val uiMode = context.resources.configuration.uiMode
            return uiMode and Configuration.UI_MODE_TYPE_MASK == Configuration.UI_MODE_TYPE_TELEVISION
        }
    }
}