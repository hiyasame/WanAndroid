package kim.bifrost.coldrain.wanandroid.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.repo.data.CollectPagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.CollectViewModel
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

    fun uncollect(id: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            ApiService.uncollect(id).ifSuccess {
                toastConcurrent("取消收藏成功")
                withContext(Dispatchers.Main) {
                    onComplete()
                }
            }.ifFailure {
                toastConcurrent("取消收藏失败: $it")
            }
        }
    }
}