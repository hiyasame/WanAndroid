package kim.bifrost.coldrain.wanandroid.repo.remote

import kim.bifrost.coldrain.wanandroid.repo.remote.bean.*
import retrofit2.Call
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
    fun loginWanAndroid(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<NetResponse<LoginData>>

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     * @param username
     * @param password
     * @param repassword
     */
    @POST("user/register")
    @FormUrlEncoded
    fun registerWanAndroid(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    ): Call<NetResponse<LoginData>>

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
    fun getArticles(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): Call<NetResponse<ArticlesData>>

    /**
     * 获取置顶文章
     *
     * @return data
     */
    @GET("article/top/json")
    fun getTopArticles(): Call<NetResponse<List<ArticleData>>>

    /**
     * 获取用户个人收藏文章
     * 需要Cookie
     *
     * @param page 页数
     * @param pageSize 每页数量
     * @return data
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollections(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): Call<NetResponse<CollectionData>>


    /**
     * 收藏文章
     * 需要Cookie
     * errorCode为0即成功
     * 反之则失败
     *
     * @param id 文章站内id
     * @return data
     */
    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Call<NetResponse<Any?>>

    /**
     * 收藏站外文章
     * 需要Cookie
     * errorCode为0即成功
     * 反之则失败
     *
     * @param id 文章站内id
     * @return
     */
    @POST("lg/collect/user_article/update/{id}/json")
    fun collectWebArticle(
        @Path("id") id: Int,
        @Query("title") title: String,
        @Query("link") link: String,
        @Query("author") author: String,
    ): Call<NetResponse<Any?>>

    /**
     * 取消收藏
     *
     * @param id id
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Call<NetResponse<Any?>>

    /**
     * 广场列表数据
     * https://wanandroid.com/user_article/list/0/json
     *
     * @param page 页码拼接在url上从0开始
     * @param pageSize 每页数量
     * @return
     */
    @GET("user_article/list/{page}/json")
    fun getSquareData(@Path("page") page: Int, @Query("page_size") pageSize: Int): Call<NetResponse<PagerArticlesData>>

    /**
     * 微信公众号文章类别
     *
     * @return
     */
    @GET("wxarticle/chapters/json")
    fun getWxArticle(): Call<NetResponse<List<WxArticleType>>>

    /**
     * 微信公众号历史文章
     *
     * @param cid
     * @param page
     * @param pageSize
     * @return
     */
    @GET("wxarticle/list/{cid}/{page}/json")
    fun getWxHistoryArticles(@Path("cid") cid: Int, @Path("page") page: Int, @Query("page_size") pageSize: Int): Call<NetResponse<ArticlesData>>

    /**
     * 获取体系数据
     *
     * @return
     */
    @GET("tree/json")
    fun getSystemData(): Call<NetResponse<List<SystemData>>>

    /**
     * 获取导航数据
     *
     * @return
     */
    @GET("navi/json")
    fun getNavigationData(): Call<NetResponse<List<NavigationData>>>

    @GET("article/list/{page}/json")
    fun getSystemDataArticles(@Path("page") page: Int, @Query("cid") cid: Int, @Query("page_size") pageSize: Int): Call<NetResponse<PagerArticlesData>>

    companion object {
        suspend fun login(username: String, password: String) =
            RetrofitHelper.service.loginWanAndroid(username, password).await()
        suspend fun register(username: String, password: String, repassword: String) =
            RetrofitHelper.service.registerWanAndroid(username, password, repassword).await()
        suspend fun info() = RetrofitHelper.service.getInfo().await()
        suspend fun banners() = RetrofitHelper.service.getBanner().await()
        suspend fun articles(page: Int, pageSize: Int) =
            RetrofitHelper.service.getArticles(page, pageSize).await()
        suspend fun topArticles() = RetrofitHelper.service.getTopArticles().await()
        suspend fun collections(page: Int, pageSize: Int) =
            RetrofitHelper.service.getCollections(page, pageSize).await()
        suspend fun collect(id: Int) = RetrofitHelper.service.collectArticle(id).await()
        suspend fun uncollect(id: Int) = RetrofitHelper.service.unCollectArticle(id).await()
        suspend fun squareData(page: Int, pageSize: Int) =
            RetrofitHelper.service.getSquareData(page, pageSize).await()
        suspend fun wxArticles() = RetrofitHelper.service.getWxArticle().await()
        suspend fun wxHistoryArticles(cid: Int, page: Int, pageSize: Int) = RetrofitHelper.service.getWxHistoryArticles(cid, page, pageSize).await()
        suspend fun systemData() = RetrofitHelper.service.getSystemData().await()
        suspend fun getNavigationData() = RetrofitHelper.service.getNavigationData().await()
        suspend fun getSystemDataArticles(page: Int, cid: Int, pageSize: Int) = RetrofitHelper.service.getSystemDataArticles(page, cid, pageSize).await()
    }
}