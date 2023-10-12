package ch.srg.dataProvider.integrationlayer.data

/**
 * Copyright (c) SRG SSR. All rights reserved.
 *
 *
 * License information is available from the LICENSE file.
 */
data class IlImage @JvmOverloads constructor(val url: String) {

    @Suppress("MagicNumber")
    enum class Size(val sizePixels: Int) {
        W240(240), W320(320), W480(480), W960(960), W1920(1920);

        companion object {
            fun getClosest(pixels: Int): Size {
                if (pixels >= W1920.sizePixels) {
                    return W1920
                }
                if (pixels <= W240.sizePixels) {
                    return W240
                }
                val sizes = values()
                var closestSize = 0
                var minDist = Int.MAX_VALUE
                for (i in sizes.indices) {
                    val dist = Math.abs(sizes[i].sizePixels - pixels)
                    if (dist <= minDist) {
                        minDist = dist
                        closestSize = i
                    }
                }
                return sizes[closestSize]
            }
        }
    }
}
