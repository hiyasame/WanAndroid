package kim.bifrost.coldrain.wanandroid.repo.remote.bean

import androidx.core.text.htmlEncode
import kim.bifrost.coldrain.wanandroid.utils.htmlDecode

/**
 * kim.bifrost.coldrain.wanandroid.api.bean.Data
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/21 22:41
 **/
data class LoginData(
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
    val username: String,
)

data class UserInfoData(
    val coinInfo: CoinInfoData?,
    val userInfo: LoginData,
) {
    data class CoinInfoData(
        val coinCount: Int,
        val level: Int,
        val nickname: String,
        val rank: Int,
        val userId: Int,
        val username: String,
    )
}

data class BannerData(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String,
)

data class ArticlesData(
    val curPage: Int,
    val datas: List<ArticleData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class ArticleData(
    val apkLink: String,
    val audit: Int,
    var author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    var collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    var shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    var title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
) {
    init {
        title = title.htmlEncode()
    }

    data class Tag(
        val name: String,
        val url: String,
    )
}

data class CollectionData(
    val curPage: Int,
    val datas: List<SingleCollectionData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
) {
    data class SingleCollectionData(
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val id: Int,
        val link: String,
        val niceDate: String,
        val origin: String,
        val originId: Int,
        val publishTime: Long,
        val title: String,
        val userId: Int,
        val visible: Int,
        val zan: Int,
    )
}

data class PagerData<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class WxArticleType(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class SystemData(
    val children: List<SystemData>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class NavigationData(
    val articles: List<ArticleData>,
    val cid: Int,
    val name: String
)

data class ProjectTypeData(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class PointChangeData(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)

data class PointData(
    val coinCount: Int,
    val rank: Int,
    val userId: Int,
    val username: String
)

data class HotKeyData(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)
