package ch.srg.dataProvider.integrationlayer.dependencies.modules

import android.net.Uri
import ch.srg.dataProvider.integrationlayer.SRGUrlFactory
import ch.srg.dataProvider.integrationlayer.data.IlHost
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IlUri

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IlVector

@Module
class SRGConfigModule(private val ilHost: IlHost) {
    @Provides
    @ConfiguredScope
    fun provideIlHost(): IlHost {
        return ilHost
    }

    @Provides
    @ConfiguredScope
    fun provideUrlFactory(): SRGUrlFactory {
        return SRGUrlFactory(ilHost)
    }

    @Provides
    @ConfiguredScope
    @IlUri
    fun providerIlBaseUri(urlFactory: SRGUrlFactory): Uri {
        return urlFactory.ilBaseUrl
    }
}