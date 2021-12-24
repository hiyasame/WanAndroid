package kim.bifrost.coldrain.wanandroid.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.repo.data.InnerSystemPagingSource
import kim.bifrost.coldrain.wanandroid.utils.getCollectFunc
import kim.bifrost.coldrain.wanandroid.view.adapter.InnerRvPagingAdapter

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.InnerSystemViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/22 11:30
 **/
class InnerSystemViewModel : ViewModel() {
    val adapterCallback = getCollectFunc<InnerRvPagingAdapter>(viewModelScope)

    fun getSystemDataArticles(cid: Int) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            InnerSystemPagingSource(cid)
        }
    ).flow
}