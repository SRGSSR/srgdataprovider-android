package ch.srg.dataProvider.integrationlayer.dependencies.modules

import android.content.Context
import android.content.res.Configuration
import ch.srg.dataProvider.integrationlayer.utils.HostInterceptor
import ch.srg.dataProvider.integrationlayer.utils.UserAgentInterceptor
import ch.srg.dataProvider.integrationlayer.utils.UserAgentUtils
import ch.srg.dataProvider.integrationlayer.utils.VectorInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
object OkHttpModule {
    private const val CACHE_SIZE = 1024 * 1024L
    private const val DEFAULT_CACHE_DIR = "il_cache"
    private const val READ_TIMEOUT_SECONDS: Long = 60
    private const val CONNECT_TIMEOUT_SECONDS: Long = 60

    fun createOkHttpClient(context: Context): OkHttpClient {
        val vector = getVector(context)
        val cache = Cache(File(context.cacheDir, DEFAULT_CACHE_DIR), CACHE_SIZE)
        val builder = OkHttpClient.Builder()
        builder.cache(cache)
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        builder.addInterceptor(logging)
        builder.addInterceptor(UserAgentInterceptor(UserAgentUtils.createUserAgent(context)))
        builder.addInterceptor(HostInterceptor())
        builder.addInterceptor(VectorInterceptor(vector))
        builder.readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        builder.connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        return builder.build()
    }

    private fun getVector(context: Context): String {
        return if (isAndroidTV(context)) "TVPLAY" else "APPPLAY"
    }

    private fun isAndroidTV(context: Context): Boolean {
        val uiMode = context.resources.configuration.uiMode
        return uiMode and Configuration.UI_MODE_TYPE_MASK == Configuration.UI_MODE_TYPE_TELEVISION
    }
}
