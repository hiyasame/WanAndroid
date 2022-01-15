package kim.bifrost.coldrain.wanandroid.repo.data

import kim.bifrost.coldrain.wanandroid.base.BasePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.FindPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/19 9:41
 **/
class FindPagingSource : BasePagingSource<ArticleData>(
    {
        PagingRepository.getSquareData(it)
    }
)