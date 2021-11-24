package kim.bifrost.coldrain.wanandroid.repo.remote.bean

import com.google.gson.annotations.SerializedName

/**
 * kim.bifrost.coldrain.wanandroid.api.bean.Data
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/21 22:41
 **/
data class LoginData(
    val data: UserInfo?,
    val errorCode: Int,
    val errorMsg: String,
) {
    data class UserInfo(
        val admin: Boolean,
        val chapterTops: MutableList<String>,
        val coinCount: Int,
        val collectIds: MutableList<String>,
        val email: String,
        val icon: String,
        val id: Long,
        val nickname: String,
        val password: String,
        val token: String,
        val type: Int,
        val username: String
    )

    fun success(): Boolean {
        return errorCode == 0
    }
}

data class UserInfoData(
    val data: InternalData?,
    val errorCode: Int,
    val errorMsg: String
) {
    data class InternalData(
        val coinInfo: CoinInfoData?,
        val userInfo: LoginData.UserInfo?
    )

    data class CoinInfoData(
        val coinCount: Int,
        val level: Int,
        val nickname: String,
        val rank: Int,
        val userId: Int,
        val username: String
    )
}
