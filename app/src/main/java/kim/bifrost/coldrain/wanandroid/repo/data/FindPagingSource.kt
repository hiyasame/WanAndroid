package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.SquareData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.FindPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/19 9:41
 **/
class FindPagingSource : PagingSource<Int, SquareData.InnerData>() {
    override fun getRefreshKey(state: PagingState<Int, SquareData.InnerData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SquareData.InnerData> {
        val pos = params.key ?: 0
        return kotlin.runCatching {
            // 通过网络请求获取数据
            val pageList = PagingRepository.getSquareData(pos)
            LoadResult.Page(
                pageList,
                if (pos <= 1) null else pos - 1,
                if (pageList.isEmpty()) null else pos + 1
            )
        }.getOrElse { LoadResult.Error(it) }
    }
}