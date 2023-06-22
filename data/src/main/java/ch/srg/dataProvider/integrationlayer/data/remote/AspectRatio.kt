package ch.srg.dataProvider.integrationlayer.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AspectRatio(val numerator: Int, val denominator: Int) {

    override fun toString(): String {
        return "$numerator:$denominator"
    }

    companion object {
        /**
         * Parse Aspect ratio writing with this numerator:denominator format.
         * Example : 1:1, 16:9, 9:16
         */
        fun parse(str: String): AspectRatio {
            val numeratorDenominatorString = str.split(":")
            require(numeratorDenominatorString.size == 2) { "Expected rational as numerator:denominator but is $str" }
            val numerator = numeratorDenominatorString[0].toInt()
            val denominator = numeratorDenominatorString[1].toInt()
            return AspectRatio(numerator, denominator)
        }
    }
}
