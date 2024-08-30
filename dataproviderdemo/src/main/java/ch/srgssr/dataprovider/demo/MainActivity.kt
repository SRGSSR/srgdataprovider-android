package ch.srgssr.dataprovider.demo

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ch.srg.dataProvider.integrationlayer.dependencies.modules.IlServiceModule
import ch.srg.dataProvider.integrationlayer.dependencies.modules.OkHttpModule
import ch.srg.dataProvider.integrationlayer.request.IlHost
import ch.srg.dataProvider.integrationlayer.request.IlService
import ch.srg.dataProvider.integrationlayer.request.parameters.Bu
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity(R.layout.activity_main) {

    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var messageTextView: TextView

    private lateinit var okHttp: OkHttpClient
    private lateinit var ilService: IlService

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        loadingProgressBar = findViewById(R.id.loading_pb)
        messageTextView = findViewById(R.id.message_tv)

        okHttp = OkHttpModule.createOkHttpClient(this)
        ilService = IlServiceModule.createIlService(okHttp, ilHost = IlHost.PROD)

        getTvLatestEpisodes()
    }

    private fun getTvLatestEpisodes() {
        val tvLatestEpisodesFlow = flow {
            emit(ilService.getTvLatestEpisodes(Bu.RTS))
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                tvLatestEpisodesFlow
                    .catch { exception ->
                        loadingProgressBar.visibility = View.GONE
                        messageTextView.text = exception.message
                        messageTextView.visibility = View.VISIBLE
                    }
                    .collect { tvLatestEpisodes ->
                        val episodesNames = tvLatestEpisodes.list.joinToString(separator = "\n") {
                            "${it.show?.title} - ${it.title}"
                        }

                        loadingProgressBar.visibility = View.GONE
                        messageTextView.text = episodesNames
                        messageTextView.visibility = View.VISIBLE
                    }
            }
        }
    }
}
