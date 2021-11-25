package kim.bifrost.coldrain.wanandroid.viewmodel

import androidx.lifecycle.ViewModel
import kim.bifrost.coldrain.wanandroid.repo.data.UserData

/**
 * kim.bifrost.coldrain.wanandroid.viewmodel.MainViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/24 0:15
 **/
class MainViewModel : ViewModel() {

    fun logout() {
        if (UserData.isLogged) {
            UserData.userInfoData = null
            UserData.releaseLocalData()
        }
    }
}