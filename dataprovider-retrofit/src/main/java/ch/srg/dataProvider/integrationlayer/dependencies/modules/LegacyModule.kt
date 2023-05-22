package ch.srg.dataProvider.integrationlayer.dependencies.modules

import ch.srg.dataProvider.integrationlayer.legacy.LegacyIlDataProviderRemote
import ch.srg.dataProvider.integrationlayer.legacy.requests.LegacyIlDataProvider
import ch.srg.dataProvider.integrationlayer.legacy.requests.LegacyIlMediaCompositionService
import ch.srg.dataProvider.integrationlayer.legacy.requests.LegacyIlService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Module
class LegacyModule {

    @Provides
    @ConfiguredScope
    fun provideMediaCompositionService(@LegacyRetrofitIntegrationLayer retrofit: Retrofit): LegacyIlMediaCompositionService {
        return retrofit.create(LegacyIlMediaCompositionService::class.java)
    }

    @Provides
    @ConfiguredScope
    fun providerIlService(@LegacyRetrofitIntegrationLayer retrofit: Retrofit): LegacyIlService {
        return retrofit.create(LegacyIlService::class.java)
    }

    @Provides
    @ConfiguredScope
    fun provideIlDataProvider(ilService: LegacyIlService): LegacyIlDataProvider {
        return LegacyIlDataProvider(ilService)
    }

    @Provides
    @ConfiguredScope
    fun providerIlDataProviderRemote(ilDataProvider: LegacyIlDataProvider): LegacyIlDataProviderRemote {
        return LegacyIlDataProviderRemote(ilDataProvider)
    }
}