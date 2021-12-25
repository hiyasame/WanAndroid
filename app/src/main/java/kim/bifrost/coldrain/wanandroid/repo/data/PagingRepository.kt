package kim.bifrost.coldrain.wanandroid.repo.data

import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.CollectionData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.PagerArticlesData

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
     * @return data
     */
    suspend fun getArticlePages(page: Int, size: Int = 20): List<ArticleData> {
        if (page == 0) return ApiService.topArticles().data?.map {
            it.apply {
                author = "置顶    >>>    $author"
                shareUser = "置顶     >>>     $shareUser"
            }
        } ?: error("failed to attach cloud data")
        return ApiService.articles(page, size).data?.datas ?: error("failed to attach cloud data")
    }

    /**
     * 获取收藏中指定页数的文章list
     *
     * @param page 当前页数
     * @param size 每页文章的量
     * @return data
     */
    suspend fun getCollectionPages(
        page: Int,
        size: Int = 20,
    ): List<CollectionData.SingleCollectionData> {
        return ApiService.collections(page, size).data?.datas
            ?: error("failed to attach cloud data")
    }

    /**
     * 获取广场Data
     *
     * @param page 当前页数
     * @param size 每页文章的量
     * @return data
     */
    suspend fun getSquareData(
        page: Int,
        size: Int = 20,
    ): List<ArticleData> {
        return ApiService.squareData(page, size).data?.datas ?: error("failed to attach cloud data")
    }

    suspend fun getWechatHistoryArticles(
        cid: Int,
        page: Int,
        size: Int = 20,
    ): List<ArticleData> {
        return ApiService.wxHistoryArticles(cid, page, size).data?.datas ?: error("failed to attach cloud data")
    }

    suspend fun getSystemArticles(
        page: Int, cid: Int, pageSize: Int = 20
    ): List<ArticleData> {
        return ApiService.getSystemDataArticles(page, cid, pageSize).data?.datas ?: error("failed to attach cloud data")
    }

    suspend fun getProjectArticles(
        page: Int, cid: Int, pageSize: Int = 20
    ): List<ArticleData> {
        return ApiService.getProjectData(page, cid, pageSize).data?.datas ?: error("failed to attach cloud data")
    }
}