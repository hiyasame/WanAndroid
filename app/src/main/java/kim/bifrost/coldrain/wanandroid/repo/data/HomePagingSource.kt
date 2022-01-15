package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.base.BasePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.PagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/29 19:38
 **/
class HomePagingSource : BasePagingSource<ArticleData>(
    {
        PagingRepository.getArticlePages(it)
    }
)