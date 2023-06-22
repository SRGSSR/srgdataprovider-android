package ch.srgssr.dataprovider.paging.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ch.srg.dataProvider.integrationlayer.data.remote.ListResult

/**
 * Copyright (c) SRG SSR. All rights reserved.
 * <p>
 * License information is available from the LICENSE file.
 */
@Suppress("TooGenericExceptionCaught")
class NextUrlPagingSource<Value : Any>(
    private val initialCall: suspend (pageSize: Int) -> ListResult<Value>?,
    private val nextCall: suspend (next: String) -> ListResult<Value>?
) : PagingSource<String, Value>() {

    override fun getRefreshKey(state: PagingState<String, Value>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Value> {
        return try {
            val nextUrl = params.key // PageSize+PrefetchDistance
            val listResult = if (nextUrl == null)
                initialCall.invoke(params.loadSize)
            else
                nextCall.invoke(nextUrl)
            val data = listResult?.list
            LoadResult.Page(data.orEmpty(), prevKey = null, nextKey = listResult?.next)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
