package ch.srg.dataProvider.integrationlayer.dependencies.modules

import android.net.Uri
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @ConfiguredScope
    @RetrofitIntegrationLayer
    fun provideRetrofitBase(@IlUri uri: Uri, httpClient: OkHttpClient, gsonFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonFactory)
            .client(httpClient)
            .baseUrl(uri.toString())
            .build()
    }
}
