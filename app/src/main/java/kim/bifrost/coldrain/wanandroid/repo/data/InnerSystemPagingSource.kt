package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.base.BasePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.InnerSystemPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/24 20:28
 **/
class InnerSystemPagingSource(private val cid: Int) : BasePagingSource<ArticleData>(
    {
        PagingRepository.getSystemArticles(it, cid)
    }
)