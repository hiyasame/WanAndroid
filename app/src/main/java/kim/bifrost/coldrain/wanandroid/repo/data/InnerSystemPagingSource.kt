package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.InnerSystemPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/24 20:28
 **/
class InnerSystemPagingSource(private val cid: Int) : PagingSource<Int, ArticleData>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        val pos = params.key ?: 0
        return kotlin.runCatching {
            // 通过网络请求获取数据
            val pageList = PagingRepository.getSystemArticles(pos, cid)
            LoadResult.Page(
                pageList,
                if (pos <= 1) null else pos - 1,
                if (pageList.isEmpty()) null else pos + 1
            )
        }.getOrElse { LoadResult.Error(it) }
    }
}