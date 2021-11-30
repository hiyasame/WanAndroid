package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.PagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/29 19:38
 **/
class HomePagingSource : PagingSource<Int, ArticleData>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        val pos = params.key ?: START_INDEX
        return try {
            // Get data from DataSource
                val pageList = PagingRepository.getArticlePages(pos)
                // Return data to RecyclerView by LoadResult
                LoadResult.Page(
                    pageList,
                    if (pos <= START_INDEX) null else pos - 1,
                    if (pageList.isEmpty()) null else pos + 1
                )
        } catch (exception: Exception) {
            // Return exception by LoadResult
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val START_INDEX = 0
    }
}