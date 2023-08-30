package ch.srg.dataProvider.integrationlayer.dependencies.modules

import ch.srg.dataProvider.integrationlayer.SRGUrlFactory
import ch.srg.dataProvider.integrationlayer.data.DataProviderJson
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.IlService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
object IlServiceModule {

    @JvmOverloads
    fun createIlService(
        httpClient: OkHttpClient,
        ilHost: IlHost = IlHost.PROD,
    ): IlService {
        val uri = SRGUrlFactory(ilHost).ilBaseUrl
        return Retrofit.Builder()
            .addConverterFactory(DataProviderJson.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .baseUrl(uri.toString())
            .build()
            .create(IlService::class.java)
    }
}
