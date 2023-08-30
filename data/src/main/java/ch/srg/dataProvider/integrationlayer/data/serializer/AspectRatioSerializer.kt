package ch.srg.dataProvider.integrationlayer.data.serializer

import ch.srg.dataProvider.integrationlayer.data.remote.AspectRatio
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AspectRatioSerializer : KSerializer<AspectRatio> {

    override val descriptor = PrimitiveSerialDescriptor("AspectRatio", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): AspectRatio {
        val string = decoder.decodeString()
        return AspectRatio.parse(string)
    }

    override fun serialize(encoder: Encoder, value: AspectRatio) {
        encoder.encodeString(value.toString())
    }
}
