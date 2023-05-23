package ch.srg.dataProvider.integrationlayer.request.parameters

import ch.srg.dataProvider.integrationlayer.data.Vendor

/**
 * Bu parameter for Il call
 *
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
data class Bu(val name: String) {
    constructor(vendor: Vendor) : this(vendor.name.lowercase())

    override fun toString(): String {
        return name
    }

    companion object {
        val SRF = Bu("srf")
        val RTS = Bu("rts")
        val RTR = Bu("rtr")
        val SWI = Bu("swi")
        val RSI = Bu("rsi")
    }
}
