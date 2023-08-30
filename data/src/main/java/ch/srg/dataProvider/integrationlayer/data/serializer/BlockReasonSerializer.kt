package ch.srg.dataProvider.integrationlayer.data.serializer

import ch.srg.dataProvider.integrationlayer.data.remote.BlockReason
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object BlockReasonSerializer : KSerializer<BlockReason> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BlockReason", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): BlockReason {
        val string = decoder.decodeString()
        return BlockReason.parseValue(string)
    }

    override fun serialize(encoder: Encoder, value: BlockReason) {
        encoder.encodeString(value.toString())
    }
}
