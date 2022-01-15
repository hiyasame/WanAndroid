package kim.bifrost.coldrain.wanandroid.view.viewmodel

import androidx.lifecycle.ViewModel
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.SearchViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2022/1/14 13:57
 **/
class SearchViewModel : ViewModel() {
    suspend fun getHotKeys() = ApiService.getHotKeys()
}