package ch.srg.dataProvider.integrationlayer.dependencies.modules

import ch.srg.dataProvider.integrationlayer.data.DataProviderMoshi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Module
class MoshiModule {

    @Provides
    @ConfiguredScope
    fun providerMoshi(): Moshi {
        return DataProviderMoshi.moshi
    }
}
