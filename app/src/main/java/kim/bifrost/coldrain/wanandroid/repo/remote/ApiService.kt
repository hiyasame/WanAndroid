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
    suspend fun loginWanAndroid(
        @Field("username") username: String,
        @Field("password") password: String,
    ): NetResponse<LoginData>

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     * @param username
     * @param password
     * @param repassword
     */
    @POST("user/register")
    @FormUrlEncoded
    suspend fun registerWanAndroid(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String,
    ): NetResponse<LoginData>

    /**
     * 获取用户信息，需要cookie
     *
     * @return 用户信息
     */
    @GET("user/lg/userinfo/json")
    suspend fun getInfo(): NetResponse<UserInfoData>

    /**
     * 获取Banner
     *
     * @return
     */
    @GET("banner/json")
    suspend fun getBanner(): NetResponse<List<BannerData>>

    /**
     * 获取文章
     *
     * @param page 页数
     * @param pageSize 每页的数量
     * @return data
     */
    @GET("article/list/{page}/json")
    suspend fun getArticles(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): NetResponse<ArticlesData>

    /**
     * 获取置顶文章
     *
     * @return data
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): NetResponse<List<ArticleData>>

    /**
     * 获取用户个人收藏文章
     * 需要Cookie
     *
     * @param page 页数
     * @param pageSize 每页数量
     * @return data
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getCollections(
        @Path("page") page: Int,
        @Query("page_size") pageSize: Int,
    ): NetResponse<CollectionData>


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
    suspend fun collectArticle(@Path("id") id: Int): NetResponse<Any?>

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
    suspend fun collectWebArticle(
        @Path("id") id: Int,
        @Query("title") title: String,
        @Query("link") link: String,
        @Query("author") author: String,
    ): NetResponse<Any?>

    /**
     * 取消收藏
     *
     * @param id id
     * @return
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id") id: Int): NetResponse<Any?>

    /**
     * 广场列表数据
     * https://wanandroid.com/user_article/list/0/json
     *
     * @param page 页码拼接在url上从0开始
     * @param pageSize 每页数量
     * @return
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int, @Query("page_size") pageSize: Int): NetResponse<PagerData<ArticleData>>

    /**
     * 微信公众号文章类别
     *
     * @return
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): NetResponse<List<WxArticleType>>

    /**
     * 微信公众号历史文章
     *
     * @param cid
     * @param page
     * @param pageSize
     * @return
     */
    @GET("wxarticle/list/{cid}/{page}/json")
    suspend fun getWxHistoryArticles(@Path("cid") cid: Int, @Path("page") page: Int, @Query("page_size") pageSize: Int): NetResponse<ArticlesData>

    /**
     * 获取体系数据
     *
     * @return
     */
    @GET("tree/json")
    suspend fun getSystemData(): NetResponse<List<SystemData>>

    /**
     * 获取导航数据
     *
     * @return
     */
    @GET("navi/json")
    suspend fun getNavigationData(): NetResponse<List<NavigationData>>

    /**
     * 获取体系文章
     *
     * @param page 页码
     * @param cid id
     * @param pageSize 每页大小
     * @return
     */
    @GET("article/list/{page}/json")
    suspend fun getSystemDataArticles(@Path("page") page: Int, @Query("cid") cid: Int, @Query("page_size") pageSize: Int): NetResponse<PagerData<ArticleData>>

    /**
     * 获取项目类型
     *
     * @return
     */
    @GET("project/tree/json")
    suspend fun getProjectTypes(): NetResponse<List<ProjectTypeData>>

    /**
     * 项目文章
     *
     * @param page
     * @param cid
     * @param pageSize
     * @return
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectData(@Path("page") page: Int, @Query("cid") cid: Int, @Query("page_size") pageSize: Int): NetResponse<PagerData<ArticleData>>

    /**
     * 积分变动
     *
     * @return
     */
    @GET("lg/coin/list/1/json")
    suspend fun getPointChangeList(): NetResponse<PagerData<PointChangeData>>

    /**
     * 获取积分信息
     *
     * @return
     */
    @GET("lg/coin/userinfo/json")
    suspend fun getPointProfile(): NetResponse<PointData>

    /**
     * 获取热词
     *
     * @return
     */
    @GET("hotkey/json")
    suspend fun getHotKeys(): NetResponse<List<HotKeyData>>

    /**
     * 搜索文章
     *
     * @param key 关键词
     * @param page_size
     * @param page 从0开始
     * @return
     */
    @POST("article/query/{page}/json")
    suspend fun search(@Path("page") page: Int , @Query("k") key: String, @Query("page_size") page_size: Int): NetResponse<PagerData<ArticleData>>

    companion object: ApiService by RetrofitHelper.service
}