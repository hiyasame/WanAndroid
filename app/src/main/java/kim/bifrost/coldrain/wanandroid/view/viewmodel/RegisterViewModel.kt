package kim.bifrost.coldrain.wanandroid.view.viewmodel

import androidx.lifecycle.*
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.RegisterViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/23 19:28
 **/
class RegisterViewModel(
    var account: String?,
    var password: String?,
    var repassword: String?
    ) : ViewModel() {

    private val _registerLiveData = MutableLiveData<Boolean>()

    val registerLiveData: LiveData<Boolean>
        get() = _registerLiveData

    // 这里的代码跟LoginModel的有些耦合
    fun postRegister() {
        assertConditions(account.equals(null, ""), password.equals(null, ""), repassword.equals(null, "")) {
            toast("账号或密码不能为空")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val data = ApiService.register(account!!, password!!, repassword!!)
                data.errorMsg.isEmpty().elseThen {
                    // 注册失败逻辑
                    toastConcurrent(data.errorMsg)
                    return@launch
                }
                data.success().then {
                    // 注册成功逻辑
                    toastConcurrent("注册成功!")
                    UserData.isLogged = true
                    UserData.userInfoData = ApiService.info().data
                    _registerLiveData.postValue(true)
                }
            }.onFailure {
                it.printStackTrace()
                toastConcurrent("未知错误")
            }
        }
    }

    class Factory(private val account: String?, private val password: String?, private val repassword: String?): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(account, password, repassword) as T
        }
    }
}