package kim.bifrost.coldrain.wanandroid.view.viewmodel

import androidx.lifecycle.ViewModel
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.PointViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2022/1/10 20:15
 **/
class PointViewModel : ViewModel() {
    suspend fun getPointChangeList() = ApiService.getPointChangeList()

    suspend fun getPointData() = ApiService.getPointProfile()
}