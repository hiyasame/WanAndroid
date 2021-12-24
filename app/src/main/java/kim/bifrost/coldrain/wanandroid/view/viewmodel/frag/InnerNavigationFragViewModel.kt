package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import androidx.lifecycle.ViewModel
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerNavigationFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/20 21:14
 **/
class InnerNavigationFragViewModel : ViewModel() {
    val naviData = flow {
        while (true) {
            try {
                emit(ApiService.getNavigationData())
                break
            } catch (t: Throwable) {
                kotlinx.coroutines.delay(TimeUnit.SECONDS.toMillis(30))
            }
        }
    }
}