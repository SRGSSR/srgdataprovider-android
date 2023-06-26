package ch.srgssr.dataprovider.paging.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ch.srg.dataProvider.integrationlayer.data.remote.ListResult
import java.io.IOException
import kotlin.math.min

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
class UrnsPagingSource<Value : Any>(
    private val urns: List<String>,
    private val call: suspend (urns: List<String>) -> ListResult<Value>?,
) : PagingSource<Int, Value>() {
    private val dataCount: Int = urns.size

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        if (urns.isEmpty()) {
            return LoadResult.Page(listOf(), null, null)
        }
        return try {
            val loadSize = min(params.loadSize, MAX_IL_URNS_PER_CALL)
            val startPosition: Int = min(dataCount - 1, params.key ?: 0) // inclusive subList
            val endPosition = min(dataCount, startPosition + loadSize) // exclusive subList
            val subUrnList: List<String> = urns.subList(startPosition, endPosition)
            if (subUrnList.isEmpty()) {
                return LoadResult.Page(listOf(), null, null)
            }
            val listResult = call.invoke(subUrnList)
            val data = listResult?.list.orEmpty()
            val nextPosition = if (endPosition >= dataCount) null else endPosition
            LoadResult.Page(data, null, nextPosition)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    companion object {
        /**
         * Integration layer hard limitation urns list query
         */
        const val MAX_IL_URNS_PER_CALL = 50
    }
}
