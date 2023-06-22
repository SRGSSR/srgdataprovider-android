package ch.srg.dataProvider.integrationlayer.data.adapter

import ch.srg.dataProvider.integrationlayer.data.remote.BlockReason
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class BlockReasonAdapter {

    @FromJson fun fromJson(json: String): BlockReason {
        return BlockReason.parseValue(json)
    }

    @ToJson fun toJson(blockReason: BlockReason): String {
        return blockReason.toString()
    }
}
