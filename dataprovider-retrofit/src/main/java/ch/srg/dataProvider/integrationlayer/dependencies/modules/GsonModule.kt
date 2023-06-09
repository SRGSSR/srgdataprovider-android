package ch.srg.dataProvider.integrationlayer.dependencies.modules

import ch.srg.dataProvider.integrationlayer.data.gson.IlGson
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Module
class GsonModule {

    @Provides
    fun provideGsonBuilder(): GsonBuilder {
        return IlGson.createBuilder()
    }

    @Provides
    @ConfiguredScope
    fun providerGson(builder: GsonBuilder): Gson {
        return builder.create()
    }
}
