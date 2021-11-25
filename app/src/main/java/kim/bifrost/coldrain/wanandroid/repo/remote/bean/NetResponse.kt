package kim.bifrost.coldrain.wanandroid.repo.remote.bean

/**
 * kim.bifrost.coldrain.wanandroid.repo.remote.bean.NetResponse
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/26 0:02
 **/
data class NetResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
) {
    fun success(): Boolean = errorCode == 0
}