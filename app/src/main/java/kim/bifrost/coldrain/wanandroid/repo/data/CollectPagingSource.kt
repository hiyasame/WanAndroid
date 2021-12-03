package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.CollectionData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.CollectPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/30 20:33
 **/
class CollectPagingSource : PagingSource<Int, CollectionData.SingleCollectionData>() {
    override fun getRefreshKey(state: PagingState<Int, CollectionData.SingleCollectionData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionData.SingleCollectionData> {
        val pos = params.key ?: 0
        return kotlin.runCatching {
            // 通过网络请求获取数据
            val pageList = PagingRepository.getCollectionPages(pos)
            LoadResult.Page(
                pageList,
                if (pos <= 1) null else pos - 1,
                if (pageList.isEmpty()) null else pos + 1
            )
        }.getOrElse { LoadResult.Error(it) }
    }
}