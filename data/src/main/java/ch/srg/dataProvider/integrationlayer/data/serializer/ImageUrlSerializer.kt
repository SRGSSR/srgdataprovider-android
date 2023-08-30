package ch.srg.dataProvider.integrationlayer.data.serializer

import ch.srg.dataProvider.integrationlayer.data.ImageUrl
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ImageUrlSerializer : KSerializer<ImageUrl> {
    override val descriptor = PrimitiveSerialDescriptor("ImageUrl", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ImageUrl {
        val string = decoder.decodeString()
        return ImageUrl(string)
    }

    override fun serialize(encoder: Encoder, value: ImageUrl) {
        encoder.encodeString(value.rawUrl)
    }
}
