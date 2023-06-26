package ch.srg.dataProvider.integrationlayer.dependencies.components;

import android.app.Application;

import androidx.annotation.NonNull;

import ch.srg.dataProvider.integrationlayer.request.IlHost;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.IlAppModule;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.SRGConfigModule;

final public class DataProviderDependencies {

    private DataProviderDependencies() {
    }

    public static IlDataProviderComponent create(@NonNull Application application, @NonNull IlHost ilHost) {
        return DaggerIlDataProviderComponent.builder()
            .ilAppModule(new IlAppModule(application))
            .sRGConfigModule(new SRGConfigModule(ilHost))
            .build();
    }
}
