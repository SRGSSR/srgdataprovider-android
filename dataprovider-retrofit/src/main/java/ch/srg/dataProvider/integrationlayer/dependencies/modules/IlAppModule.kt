package ch.srg.dataProvider.integrationlayer.dependencies.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by seb on 18/08/14.
 */
@Module
class IlAppModule(private val app: Application) {
    @Provides
    @ConfiguredScope
    fun provideApplication(): Application {
        return app
    }

    @Provides
    @ConfiguredScope
    fun provideContext(): Context {
        return app.applicationContext
    }
}
