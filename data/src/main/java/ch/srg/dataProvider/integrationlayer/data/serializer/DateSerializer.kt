package ch.srg.dataProvider.integrationlayer.data.serializer

import ch.srg.dataProvider.integrationlayer.data.ISO8601DateParser
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.Date

class DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Date {
        val string = decoder.decodeString()
        return ISO8601DateParser().parseDate(string)
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(ISO8601DateParser().format(value))
    }
}
