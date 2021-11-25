package kim.bifrost.coldrain.wanandroid.viewmodel.frag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kim.bifrost.coldrain.wanandroid.repo.remote.RetrofitHelper
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.BannerData
import kim.bifrost.coldrain.wanandroid.utils.then
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * kim.bifrost.coldrain.wanandroid.viewmodel.frag.HomeFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/25 19:24
 **/
class HomeFragViewModel : ViewModel() {

    val banners = arrayListOf<BannerData>()

    fun refresh() {
        banners.clear()
        viewModelScope.launch(Dispatchers.IO) {
            val body = RetrofitHelper.service.getBanner().execute().body()!!
            body.success().then { banners.addAll(body.data) }
        }
    }

    init {
        refresh()
    }
}