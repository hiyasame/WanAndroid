package kim.bifrost.coldrain.wanandroid.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.base.BasePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.SearchResultViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2022/1/15 14:55
 **/
class SearchResultViewModel(val key: String) : ViewModel() {
    val pageList = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            BasePagingSource {
                ApiService.search(it, key, 20).data!!.datas
            }
        }
    ).flow
}