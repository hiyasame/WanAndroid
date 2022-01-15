package kim.bifrost.coldrain.wanandroid.repo.data

import kim.bifrost.coldrain.wanandroid.base.BasePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.InnerProjectPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/25 21:19
 **/
class InnerProjectPagingSource(private val cid: Int) : BasePagingSource<ArticleData>(
    {
        PagingRepository.getProjectArticles(it, cid)
    }
)