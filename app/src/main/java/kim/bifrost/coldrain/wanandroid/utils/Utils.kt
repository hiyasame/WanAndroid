package kim.bifrost.coldrain.wanandroid.utils

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * kim.bifrost.coldrain.wanandroid.utils.Utils
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/21 0:14
 **/
@RequiresApi(Build.VERSION_CODES.O)
fun randomColor() = Color.valueOf(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

fun View.getMarginLayoutParams() = if (layoutParams is ViewGroup.MarginLayoutParams) layoutParams as ViewGroup.MarginLayoutParams else ViewGroup.MarginLayoutParams(
    layoutParams).apply { layoutParams = this }

fun <T: BasePagingAdapter<*, ArticleData>> getCollectFunc(viewModelScope: CoroutineScope) : BasePagingAdapter.Holder<*>.(T, View) -> Unit
    = { adapter, view ->
        view.setOnClickListener {
            if (UserData.isLogged) {
                val data = adapter.getItemOut(bindingAdapterPosition)!!
                viewModelScope.launch(Dispatchers.IO) {
                    if (data.collect) {
                        ApiService.uncollect(data.id).ifSuccess { _ ->
                            withContext(Dispatchers.Main) {
                                toast("已取消收藏")
                                data.collect = false
                                (it as ImageView).setImageResource(R.drawable.ic_not_like)
                                it.clearColorFilter()
                            }
                        }.ifFailure {
                            toastConcurrent("网络请求失败: $it")
                        }
                    } else {
                        ApiService.collect(data.id).ifSuccess { _ ->
                            withContext(Dispatchers.Main) {
                                toastConcurrent("已收藏")
                                data.collect = true
                                (it as ImageView).setImageResource(R.drawable.ic_like)
                                it.setColorFilter(Color.parseColor("#CDF68A8A"))
                            }
                        }.ifFailure {
                            toastConcurrent("网络请求失败: $it")
                        }
                    }
                }
            }
        }
    }

fun RecyclerView.scrollToTop() {
    if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 20) {
        scrollToPosition(0)
    } else {
        smoothScrollToPosition(0)
    }
}