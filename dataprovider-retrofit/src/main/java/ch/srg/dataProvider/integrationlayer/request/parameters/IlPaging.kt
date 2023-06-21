package ch.srg.dataProvider.integrationlayer.request.parameters

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
sealed class IlPaging(value: String) : IlParam(value) {
    object Unlimited : IlPaging("unlimited")
    class Size(pageSize: Int) : IlPaging(pageSize.toString())

    fun Int.toIlPaging() = Size(this)
}
