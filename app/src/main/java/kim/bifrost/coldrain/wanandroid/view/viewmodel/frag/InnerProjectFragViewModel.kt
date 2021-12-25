package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.repo.data.InnerProjectPagingSource
import kim.bifrost.coldrain.wanandroid.repo.data.InnerSystemPagingSource
import kim.bifrost.coldrain.wanandroid.utils.getCollectFunc
import kim.bifrost.coldrain.wanandroid.view.adapter.InnerProjectRvAdapter

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerProjectFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/25 20:50
 **/
class InnerProjectFragViewModel : ViewModel() {
    fun getProjectArticles(cid: Int) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            InnerProjectPagingSource(cid)
        }
    ).flow

    val onCollect = getCollectFunc<InnerProjectRvAdapter>(viewModelScope)
}