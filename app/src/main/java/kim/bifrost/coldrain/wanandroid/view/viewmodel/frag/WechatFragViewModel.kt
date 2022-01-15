package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import androidx.lifecycle.ViewModel
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kotlinx.coroutines.flow.flow

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.WechatFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/19 20:34
 **/
class WechatFragViewModel : ViewModel() {
    val wechatArticleTypes = flow {
        emit(ApiService.getWxArticle().data!!)
    }
}