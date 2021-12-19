package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
import kim.bifrost.coldrain.wanandroid.repo.data.InnerWechatPagingSource
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.adapter.FindPagingDataAdapter
import kim.bifrost.coldrain.wanandroid.view.adapter.InnerWechatPagingDataAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerWechatFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/19 21:59
 **/
class InnerWechatFragViewModel : ViewModel() {
    fun getHistoryArticleList(cid: Int): Flow<PagingData<ArticleData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                InnerWechatPagingSource(cid)
            }
        ).flow
    }

    val collectCallback: BasePagingAdapter.Holder<HomeRvItemBinding>.(InnerWechatPagingDataAdapter) -> Unit
        get() = { adapter ->
            binding.homeButtonLike.setOnClickListener {
                if (UserData.isLogged) {
                    val data = adapter.getItemOut(bindingAdapterPosition)!!
                    viewModelScope.launch(Dispatchers.IO) {
                        if (data.collect) {
                            ApiService.uncollect(data.id).ifSuccess {
                                withContext(Dispatchers.Main) {
                                    toast("已取消收藏")
                                    data.collect = false
                                    (it as ImageView).setImageResource(R.drawable.ic_not_like)
                                    it.clearColorFilter()
                                    adapter.refresh()
                                }
                            }.ifFailure {
                                toastConcurrent("网络请求失败: $it")
                            }
                        } else {
                            ApiService.collect(data.id).ifSuccess {
                                withContext(Dispatchers.Main) {
                                    toastConcurrent("已收藏")
                                    data.collect = true
                                    (it as ImageView).setImageResource(R.drawable.ic_like)
                                    it.setColorFilter(Color.parseColor("#CDF68A8A"))
                                    adapter.refresh()
                                }
                            }.ifFailure {
                                toastConcurrent("网络请求失败: $it")
                            }
                        }
                    }
                }
            }
        }
}