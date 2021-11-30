package kim.bifrost.coldrain.wanandroid.viewmodel.frag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.repo.data.HomePagingSource
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.BannerData
import kim.bifrost.coldrain.wanandroid.utils.then
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.viewmodel.frag.HomeFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/25 19:24
 **/
class HomeFragViewModel : ViewModel() {

    val banners = arrayListOf<BannerData>()

    private val _updater = MutableLiveData<Int>()

    val articleList = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            HomePagingSource()
        }
    ).flow

    val updater: LiveData<Int>
        get() = _updater

    fun refresh() {
        banners.clear()
        viewModelScope.launch(Dispatchers.IO) {
            val body = ApiService.banners()
            body.success().then { banners.addAll(body.data!!) }
            withContext(Dispatchers.Main) {
                _updater.value = 1
            }
        }
    }
}