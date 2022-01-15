package kim.bifrost.coldrain.wanandroid.repo.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kim.bifrost.coldrain.wanandroid.base.BasePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.CollectionData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.CollectPagingSource
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/30 20:33
 **/
class CollectPagingSource : BasePagingSource<CollectionData.SingleCollectionData>(
    {
        PagingRepository.getCollectionPages(it)
    }
)