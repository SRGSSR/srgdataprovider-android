package ch.srg.dataProvider.integrationlayer.data.remote

import ch.srg.dataProvider.integrationlayer.data.serializer.AspectRatioSerializer
import kotlinx.serialization.Serializable

@Suppress("MemberVisibilityCanBePrivate")
@Serializable(with = AspectRatioSerializer::class)
data class AspectRatio(val numerator: Int, val denominator: Int) {

    override fun toString(): String {
        return "$numerator$SEPARATOR$denominator"
    }

    companion object {
        val Infinity = AspectRatio(1, 0)
        val Zero = AspectRatio(0, 1)

        private const val SEPARATOR = ":"

        /**
         * Parse Aspect ratio writing with this numerator:denominator format.
         * Examples: 1:1, 16:9, 9:16
         */
        fun parse(str: String): AspectRatio {
            val numeratorDenominatorString = str.split(SEPARATOR)
            require(numeratorDenominatorString.size == 2) { "Expected rational as numerator:denominator but is $str" }
            val numerator = numeratorDenominatorString[0].toInt()
            val denominator = numeratorDenominatorString[1].toInt()
            if (denominator == 0) return Infinity
            if (numerator == 0) return Zero
            return AspectRatio(numerator, denominator)
        }
    }
}
