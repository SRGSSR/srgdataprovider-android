package ch.srgssr.dataprovider.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ch.srg.dataProvider.integrationlayer.data.IlHost
import ch.srg.dataProvider.integrationlayer.dependencies.components.DataProviderDependencies
import ch.srg.dataProvider.integrationlayer.dependencies.components.IlDataProviderComponent
import ch.srg.dataProvider.integrationlayer.request.parameters.Bu
import ch.srgssr.dataprovider.paging.dependencies.DataProviderPagingComponent
import ch.srgssr.dataprovider.paging.dependencies.DataProviderPagingDependencies
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var ilDataComponent: IlDataProviderComponent
    private lateinit var dataProviderComponent: DataProviderPagingComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ilDataComponent = DataProviderDependencies.create(application, IlHost.PROD)
        dataProviderComponent = DataProviderPagingDependencies.create(ilDataComponent)

        lifecycleScope.launchWhenCreated {
            dataProviderComponent.dataProviderPaging.getTvSoonExpiringMedias(Bu.RTS).collectLatest {
                Log.d("Coucou", "Data loaded ")
            }
        }
    }
}
