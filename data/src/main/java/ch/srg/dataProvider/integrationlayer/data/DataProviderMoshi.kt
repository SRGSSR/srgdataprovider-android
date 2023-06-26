package ch.srg.dataProvider.integrationlayer.data

import ch.srg.dataProvider.integrationlayer.data.adapter.AspectRatioAdapter
import ch.srg.dataProvider.integrationlayer.data.adapter.BlockReasonAdapter
import ch.srg.dataProvider.integrationlayer.data.adapter.ImageUrlAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.addAdapter

object DataProviderMoshi {

    @OptIn(ExperimentalStdlibApi::class)
    val moshi: Moshi = Moshi.Builder()
        .add(BlockReasonAdapter())
        .add(AspectRatioAdapter())
        .add(ImageUrlAdapter())
        .addAdapter(Rfc3339DateJsonAdapter())
        .build()
}
