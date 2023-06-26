package ch.srgssr.dataprovider.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ch.srg.dataProvider.integrationlayer.dependencies.components.DataProviderDependencies
import ch.srg.dataProvider.integrationlayer.dependencies.components.IlDataProviderComponent
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.parameters.Bu
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class MainActivity : AppCompatActivity() {

    private lateinit var component: IlDataProviderComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = DataProviderDependencies.create(application, IlHost.PROD)

        val flowData = flow {
            emit(component.ilService.getTvLatestEpisodes(Bu.RTS))
        }

        lifecycleScope.launchWhenResumed {
            flowData.collectLatest {
                Log.d("Coucou", "${it.list}")
            }
        }
    }
}
