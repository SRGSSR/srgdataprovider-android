package ch.srg.dataProvider.integrationlayer.request.parameters

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
sealed class IlParam(val param: String) {

    override fun toString(): String {
        return param
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IlParam

        return param == other.param
    }

    override fun hashCode(): Int {
        return param.hashCode()
    }
}
