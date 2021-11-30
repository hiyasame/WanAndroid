package kim.bifrost.coldrain.wanandroid.repo.data

import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData

/**
 * kim.bifrost.coldrain.wanandroid.repo.data.PagingRepository
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/29 20:10
 **/
object PagingRepository {
    /**
     * 获取指定页数文章的List
     *
     * @param page 当前页数
     * @param size 每页文章的量
     */
    suspend fun getArticlePages(page: Int, size: Int = 20): List<ArticleData> {
        if (page == 0) return ApiService.topArticles().data?.map { it.apply {
            author = "置顶    >>>    $author"
            shareUser = "置顶     >>>     $shareUser"
        } } ?: error("failed to attach cloud data")
        return ApiService.articles(page -1, size).data?.datas ?: error("failed to attach cloud data")
    }
}