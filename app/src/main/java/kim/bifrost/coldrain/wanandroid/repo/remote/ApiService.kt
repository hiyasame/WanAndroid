package kim.bifrost.coldrain.wanandroid.repo.remote

import kim.bifrost.coldrain.wanandroid.repo.remote.bean.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.await
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

    /**
     * 获取文章
     *
     * @param page 页数
     * @param pageSize 每页的数量
     * @return data
     */
    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: Int, @Query("page_size") pageSize: Int): Call<NetResponse<ArticlesData>>

    @GET("article/top/json")
    fun getTopArticles(): Call<NetResponse<List<ArticleData>>>

    companion object {
        suspend fun login(username: String, password: String) = RetrofitHelper.service.loginWanAndroid(username, password).await()
        suspend fun register(username: String, password: String, repassword: String) = RetrofitHelper.service.registerWanAndroid(username, password, repassword).await()
        suspend fun info() = RetrofitHelper.service.getInfo().await()
        suspend fun banners() = RetrofitHelper.service.getBanner().await()
        suspend fun articles(page: Int, pageSize: Int) = RetrofitHelper.service.getArticles(page, pageSize).await()
        suspend fun topArticles() = RetrofitHelper.service.getTopArticles().await()
    }
}