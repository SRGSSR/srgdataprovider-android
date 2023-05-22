package ch.srg.dataProvider.integrationlayer.request.parameters

import ch.srg.dataProvider.integrationlayer.data.SRGIdentifierMetadata

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class IlUrns private constructor(output: String) : IlParam(output) {
    constructor(vararg urns: String) : this(urns.joinToString(LIST_SEPARATOR))
    constructor(list: List<String>) : this(list.joinToString(LIST_SEPARATOR))

    companion object {
        const val LIST_SEPARATOR = ","

        fun <T : SRGIdentifierMetadata> from(list: List<T>): IlUrns {
            return IlUrns(output = list.joinToString(LIST_SEPARATOR) { it.urn })
        }
    }
}