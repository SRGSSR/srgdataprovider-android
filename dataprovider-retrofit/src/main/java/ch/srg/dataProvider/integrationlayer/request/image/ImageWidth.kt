package ch.srg.dataProvider.integrationlayer.request.image

import kotlin.math.abs

/**
 * Image width supported by the integration layer
 *
 * @property widthPixels The width in pixels.
 */
@Suppress("MagicNumber")
enum class ImageWidth(val widthPixels: Int) {
    W240(240),
    W320(320),
    W480(480),
    W960(960),
    W1920(1920),
    ;

    companion object {

        /**
         * Get the [ImageWidth] that matches [pixels] or the closest one.
         *
         * @param pixels The width in pixels.
         */
        fun getFromPixels(pixels: Int): ImageWidth {
            return when (pixels) {
                W240.widthPixels -> W240
                W320.widthPixels -> W320
                W480.widthPixels -> W480
                W960.widthPixels -> W960
                W1920.widthPixels -> W1920
                else -> getClosest(pixels)
            }
        }

        /**
         * Get closest [ImageWidth]
         *
         * @param widthPixels The width in pixels to get the closest [ImageWidth].
         */
        private fun getClosest(widthPixels: Int): ImageWidth {
            if (widthPixels >= W1920.widthPixels) {
                return W1920
            }
            if (widthPixels <= W240.widthPixels) {
                return W240
            }
            val sizes = entries
            var closestSize = 0
            var minDist = Int.MAX_VALUE
            for (i in sizes.indices) {
                val dist = abs(sizes[i].widthPixels - widthPixels)
                if (dist <= minDist) {
                    minDist = dist
                    closestSize = i
                }
            }
            return sizes[closestSize]
        }
    }
}
