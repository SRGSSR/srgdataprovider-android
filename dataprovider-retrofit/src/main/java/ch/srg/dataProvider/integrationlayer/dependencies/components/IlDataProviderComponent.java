package ch.srg.dataProvider.integrationlayer.dependencies.components;

import android.app.Application;
import android.content.Context;

import com.squareup.moshi.Moshi;

import ch.srg.dataProvider.integrationlayer.SRGUrlFactory;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.ConfiguredScope;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.MoshiModule;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.IlAppModule;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.IlRetrofitModule;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.IlServiceModule;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.OkHttpModule;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.SRGConfigModule;
import ch.srg.dataProvider.integrationlayer.request.IlService;
import ch.srg.dataProvider.integrationlayer.request.ImageProvider;
import ch.srg.dataProvider.integrationlayer.request.SearchProvider;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by npietri on 07.09.16.
 */
@ConfiguredScope
@Component(
    modules = {
        SRGConfigModule.class,
        IlAppModule.class,
        MoshiModule.class,
        OkHttpModule.class,
        IlRetrofitModule.class,
        IlServiceModule.class
    }
)
public interface IlDataProviderComponent {
    Application getApplication();

    Context getContext();

    SRGUrlFactory getUrlFactory();

    ImageProvider getImageProvider();

    OkHttpClient.Builder getOkHttpClientBuilder();

    OkHttpClient getOkHttpClient();

    Moshi getMoshi();

    IlService getIlService();

    SearchProvider getSearchProvider();
}
