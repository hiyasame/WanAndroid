package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import androidx.lifecycle.ViewModel
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kotlinx.coroutines.flow.flow

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerSystemFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/20 19:45
 **/
class InnerSystemFragViewModel : ViewModel() {
    val systemData = flow {
        emit(ApiService.systemData())
    }
}