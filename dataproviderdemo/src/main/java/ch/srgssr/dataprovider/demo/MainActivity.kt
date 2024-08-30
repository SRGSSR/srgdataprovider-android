package ch.srgssr.dataprovider.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ch.srg.dataProvider.integrationlayer.dependencies.modules.IlServiceModule
import ch.srg.dataProvider.integrationlayer.dependencies.modules.OkHttpModule
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.IlService
import ch.srg.dataProvider.integrationlayer.request.parameters.Bu
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {

    private lateinit var okHttp: OkHttpClient
    private lateinit var ilService: IlService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        okHttp = OkHttpModule.createOkHttpClient(this)
        ilService = IlServiceModule.createIlService(okHttp, ilHost = IlHost.PROD)

        val flowData = flow {
            emit(ilService.getTvLatestEpisodes(Bu.RTS))
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                flowData.collectLatest {
                    Log.d("Coucou", "${it.list}")
                }
            }
        }
    }
}
