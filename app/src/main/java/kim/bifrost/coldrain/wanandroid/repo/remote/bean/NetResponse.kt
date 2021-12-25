package kim.bifrost.coldrain.wanandroid.repo.remote.bean

/**
 * kim.bifrost.coldrain.wanandroid.repo.remote.bean.NetResponse
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/26 0:02
 **/
data class NetResponse<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String,
) {
    fun success(): Boolean = errorCode == 0

    inline fun ifSuccess(func: (T?) -> Unit): NetResponse<T> {
        if (success()) {
            func(data)
        }
        return this
    }

    inline fun ifFailure(func: (String) -> Unit): NetResponse<T> {
        if (!success()) {
            func(errorMsg)
        }
        return this
    }
}