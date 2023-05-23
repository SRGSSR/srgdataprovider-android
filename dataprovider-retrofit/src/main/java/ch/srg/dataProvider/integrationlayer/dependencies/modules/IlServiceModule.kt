package ch.srg.dataProvider.integrationlayer.dependencies.modules

import ch.srg.dataProvider.integrationlayer.request.IlService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
@Module
class IlServiceModule {

    @Provides
    @ConfiguredScope
    fun provideIlService(@RetrofitIntegrationLayer retrofit: Retrofit): IlService {
        return retrofit.create(IlService::class.java)
    }
}