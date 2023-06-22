package ch.srg.dataProvider.integrationlayer.dependencies.modules

import android.net.Uri
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitIntegrationLayer

@Module
class IlRetrofitModule {

    @Provides
    @ConfiguredScope
    @RetrofitIntegrationLayer
    fun provideRetrofitBase(@IlUri uri: Uri, httpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .baseUrl(uri.toString())
            .build()
    }
}
