package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.base.BasePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.InnerWechatPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/19 22:58
 **/
class InnerWechatPagingSource(private val cid: Int) : BasePagingSource<ArticleData>(
    {
        PagingRepository.getWechatHistoryArticles(cid, it)
    }
)