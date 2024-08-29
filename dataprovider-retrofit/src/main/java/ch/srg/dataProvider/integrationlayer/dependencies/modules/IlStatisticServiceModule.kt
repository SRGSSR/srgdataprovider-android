package ch.srg.dataProvider.integrationlayer.dependencies.modules

import ch.srg.dataProvider.integrationlayer.SRGUrlFactory
import ch.srg.dataProvider.integrationlayer.data.DataProviderJson
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.IlStatisticService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object IlStatisticServiceModule {

    @JvmOverloads
    fun createIlStatisticService(
        httpClient: OkHttpClient,
        ilHost: IlHost = IlHost.PROD,
    ): IlStatisticService {
        val uri = SRGUrlFactory(ilHost).ilBaseUrl
        return Retrofit.Builder()
            .addConverterFactory(DataProviderJson.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .baseUrl(uri.toString())
            .build()
            .create()
    }
}
