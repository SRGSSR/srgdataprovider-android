package ch.srg.dataProvider.integrationlayer.dependencies.modules

import ch.srg.dataProvider.integrationlayer.SRGUrlFactory
import ch.srg.dataProvider.integrationlayer.data.DataProviderMoshi
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.IlService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
            .addConverterFactory(MoshiConverterFactory.create(DataProviderMoshi.moshi))
            .client(httpClient)
            .baseUrl(uri.toString())
            .build()
            .create(IlService::class.java)
    }
}
