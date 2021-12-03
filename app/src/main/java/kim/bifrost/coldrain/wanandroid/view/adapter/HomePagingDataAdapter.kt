package kim.bifrost.coldrain.wanandroid.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
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
class HomePagingDataAdapter(private val context: Context) :
    PagingDataAdapter<ArticleData, HomePagingDataAdapter.Holder>(HomeRvItemCallBack()) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = getItem(position)!!
        val binding = holder.binding
        binding.homeRvTitle.text = data.title
        binding.homeRvDate.text = data.niceDate
        binding.homeRvHead.text = if (data.author.isEmpty()) data.shareUser else data.author
        binding.homeRvLabel.text = data.superChapterName + "/" + data.chapterName
        binding.homeButtonLike.apply {
            if (data.collect) {
                data.collect = false
                setImageResource(R.drawable.ic_like)
                setColorFilter(Color.parseColor("#CDF68A8A"))
            } else {
                setImageResource(R.drawable.ic_not_like)
                clearColorFilter()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class Holder(val binding: HomeRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val data = getItem(absoluteAdapterPosition)!!
                WebPageActivity.startActivity(context, data.link, data.title)
            }
            binding.homeButtonLike.setOnClickListener {
                if (UserData.isLogged) {
                    val data = getItem(absoluteAdapterPosition)!!
                    binding.homeButtonLike.apply {
                        if (data.collect) {
                            App.coroutineScope.launch(Dispatchers.IO) {
                                ApiService.uncollect(data.id).ifSuccess {
                                    withContext(Dispatchers.Main) {
                                        toast("已取消收藏")
                                        data.collect = false
                                        setImageResource(R.drawable.ic_not_like)
                                        clearColorFilter()
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
                                        setImageResource(R.drawable.ic_like)
                                        setColorFilter(Color.parseColor("#CDF68A8A"))
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

    class HomeRvItemCallBack : DiffUtil.ItemCallback<ArticleData>() {
        override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
            return oldItem == newItem
        }
    }
}