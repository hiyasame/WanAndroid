package kim.bifrost.coldrain.wanandroid.viewmodel

import androidx.lifecycle.*
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.RetrofitHelper
import kim.bifrost.coldrain.wanandroid.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * kim.bifrost.coldrain.wanandroid.viewmodel.LoginViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/21 20:58
 **/
class LoginViewModel(var account: String?, var password: String?) : ViewModel() {

    private val _loginLiveData = MutableLiveData(false)

    val loginLiveData: LiveData<Boolean>
        get() = _loginLiveData

    fun postLogin() {
        assertConditions(account.equals(null, ""), password.equals(null, "")) {
            toast("账号或密码不能为空")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val data = ApiService.login(account!!, password!!)
                data.errorMsg.isEmpty().elseThen {
                    // 登录失败逻辑
                    toastConcurrent(data.errorMsg)
                    return@launch
                }
                data.success().then {
                    // 登录成功逻辑
                    toastConcurrent("登录成功!")
                    UserData.isLogged = true
                    UserData.userInfoData = ApiService.info().data
                    _loginLiveData.postValue(true)
                }
            }.onFailure {
                it.printStackTrace()
                toastConcurrent("未知错误")
            }
        }
    }

    class Factory(private val account: String, private val password: String): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(account, password) as T
        }
    }

}