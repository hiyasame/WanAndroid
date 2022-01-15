package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import android.content.Context
import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.repo.data.HomePagingSource
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.BannerData
import kim.bifrost.coldrain.wanandroid.utils.then
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.activity.LoginActivity
import kim.bifrost.coldrain.wanandroid.view.adapter.HomePagingDataAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.HomeFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/25 19:24
 **/
class HomeFragViewModel : ViewModel() {

    val banners = arrayListOf<BannerData>()

    private val _updater = MutableLiveData<Int>()

    val articleList = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            HomePagingSource()
        }
    ).flow

    val updater: LiveData<Int>
        get() = _updater

    fun refresh() {
        banners.clear()
        viewModelScope.launch(Dispatchers.IO) {
            val body = ApiService.getBanner()
            body.success().then { banners.addAll(body.data!!) }
            withContext(Dispatchers.Main) {
                _updater.value = 1
            }
        }
    }

    val onCollect: HomePagingDataAdapter.Holder.(HomePagingDataAdapter, Context) -> Unit =
        { adapter, context ->
            view.findViewById<ImageView>(R.id.homeButtonLike)
                .setOnClickListener {
                    if (UserData.isLogged) {
                        val data = adapter.getItemOut(absoluteAdapterPosition)!!
                        it.apply {
                            if (data.collect) {
                                viewModelScope.launch(Dispatchers.IO) {
                                    ApiService.unCollectArticle(data.id).ifSuccess {
                                        withContext(Dispatchers.Main) {
                                            toast("已取消收藏")
                                            data.collect = false
                                            (this@apply as ImageView).setImageResource(R.drawable.ic_not_like)
                                            clearColorFilter()
                                            adapter.refresh()
                                        }
                                    }.ifFailure {
                                        toastConcurrent("网络请求失败: $it")
                                    }
                                }
                            } else {
                                viewModelScope.launch(Dispatchers.IO) {
                                    ApiService.collectArticle(data.id).ifSuccess {
                                        withContext(Dispatchers.Main) {
                                            toastConcurrent("已收藏")
                                            data.collect = true
                                            (this@apply as ImageView).setImageResource(R.drawable.ic_like)
                                            setColorFilter(Color.parseColor("#CDF68A8A"))
                                            adapter.refresh()
                                        }
                                    }.ifFailure {
                                        toastConcurrent("网络请求失败: $it")
                                    }
                                }
                            }
                        }
                    } else {
                        LoginActivity.start(context)
                    }
                }
        }

}