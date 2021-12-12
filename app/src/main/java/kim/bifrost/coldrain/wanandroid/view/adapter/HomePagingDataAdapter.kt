package kim.bifrost.coldrain.wanandroid.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
import kim.bifrost.coldrain.wanandroid.databinding.HomeVpBinding
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.activity.LoginActivity
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.HomePagingDataAdpater
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/28 0:54
 **/
class HomePagingDataAdapter(private val context: Context, val bindHeader: HomeVpBinding.() -> Unit) :
    PagingDataAdapter<ArticleData, HomePagingDataAdapter.Holder>(HomeRvItemCallBack()) {

    lateinit var headerBinding: HomeVpBinding

    lateinit var lastItemBinding: HomeRvItemBinding

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (getItemViewType(position) == 0) {
            // 回调
            bindHeader(headerBinding)
            return
        }
        val data = getItem(position)!!
        holder.view.apply {
            findViewById<TextView>(R.id.homeRvTitle).text = data.title
            findViewById<TextView>(R.id.homeRvDate).text = data.niceDate
            findViewById<TextView>(R.id.homeRvHead).text = if (data.author.isEmpty()) data.shareUser else data.author
            findViewById<TextView>(R.id.homeRvLabel).text = data.superChapterName + "/" + data.chapterName
            findViewById<ImageView>(R.id.homeButtonLike).apply {
                if (data.collect) {
                    data.collect = true
                    setImageResource(R.drawable.ic_like)
                    setColorFilter(Color.parseColor("#CDF68A8A"))
                } else {
                    data.collect = false
                    setImageResource(R.drawable.ic_not_like)
                    clearColorFilter()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        if (viewType == 0) {
            return Holder(HomeVpBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply { headerBinding = this }.root)
        }
        return Holder(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply { lastItemBinding = this }.root)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 0
        return 1
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            if (view !is ViewPager2) {
                view.setOnClickListener {
                    val data = getItem(absoluteAdapterPosition)!!
                    WebPageActivity.startActivity(context, data.link, data.title)
                }
                view.findViewById<ImageView>(R.id.homeButtonLike)
                    .setOnClickListener {
                        if (UserData.isLogged) {
                            val data = getItem(absoluteAdapterPosition)!!
                            it.apply {
                                if (data.collect) {
                                    App.coroutineScope.launch(Dispatchers.IO) {
                                        ApiService.uncollect(data.id).ifSuccess {
                                            withContext(Dispatchers.Main) {
                                                toast("已取消收藏")
                                                data.collect = false
                                                (it as ImageView).setImageResource(R.drawable.ic_not_like)
                                                it.clearColorFilter()
                                                refresh()
                                            }
                                        }.ifFailure {
                                            toastConcurrent("网络请求失败: $it")
                                        }
                                    }
                                } else {
                                    App.coroutineScope.launch(Dispatchers.IO) {
                                        ApiService.collect(data.id).ifSuccess {
                                            withContext(Dispatchers.Main) {
                                                toastConcurrent("已收藏")
                                                data.collect = true
                                                (it as ImageView).setImageResource(R.drawable.ic_like)
                                                it.setColorFilter(Color.parseColor("#CDF68A8A"))
                                                refresh()
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
    }

    class HomeRvItemCallBack : DiffUtil.ItemCallback<ArticleData>() {
        override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem == newItem
        }
    }
}