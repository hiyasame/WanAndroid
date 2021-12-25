package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import androidx.lifecycle.ViewModel
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kotlinx.coroutines.flow.flow

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.ProjectFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/25 19:21
 **/
class ProjectFragViewModel : ViewModel() {
    val types = flow {
        emit(ApiService.getProjectType())
    }
}