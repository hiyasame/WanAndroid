package kim.bifrost.coldrain.wanandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.repo.data.CollectPagingSource
import kim.bifrost.coldrain.wanandroid.repo.data.HomePagingSource

/**
 * kim.bifrost.coldrain.wanandroid.viewmodel.CollectViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/30 18:29
 **/
class CollectViewModel : ViewModel() {
    val collections = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            CollectPagingSource()
        }
    ).flow
}