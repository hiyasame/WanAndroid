package kim.bifrost.coldrain.wanandroid.repo.remote

import kim.bifrost.coldrain.wanandroid.repo.remote.bean.BannerData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.LoginData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.NetResponse
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.UserInfoData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * kim.bifrost.coldrain.wanandroid.api.APIService
 * WanAndroid
 * Retrofit接口,封装了用到的WanAndroid Api
 *
 * @author 寒雨
 * @since 2021/11/21 22:36
 **/
interface ApiService {
    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @POST("user/login")
    @FormUrlEncoded
    fun loginWanAndroid(@Field("username") username: String,
                        @Field("password") password: String): Call<NetResponse<LoginData>>

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     * @param username
     * @param password
     * @param repassword
     */
    @POST("user/register")
    @FormUrlEncoded
    fun registerWanAndroid(@Field("username") username: String,
                           @Field("password") password: String,
                           @Field("repassword") repassword: String): Call<NetResponse<LoginData>>

    /**
     * 获取用户信息，需要cookie
     *
     * @return 用户信息
     */
    @GET("user/lg/userinfo/json")
    fun getInfo(): Call<NetResponse<UserInfoData>>

    /**
     * 获取Banner
     *
     * @return
     */
    @GET("banner/json")
    fun getBanner(): Call<NetResponse<List<BannerData>>>

//    @GET("article/list/0/json")
//    fun getArticles()

}