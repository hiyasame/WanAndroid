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
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
import kim.bifrost.coldrain.wanandroid.databinding.HomeVpBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.utils.htmlDecode
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.HomePagingDataAdpater
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/28 0:54
 **/
class HomePagingDataAdapter(private val context: Context, val callback: CallBack) :
    PagingDataAdapter<ArticleData, HomePagingDataAdapter.Holder>(HomeRvItemCallBack()) {

    lateinit var headerBinding: HomeVpBinding

    lateinit var lastItemBinding: HomeRvItemBinding

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (getItemViewType(position) == 0) {
            // 回调
            callback.onBindHeader(headerBinding)
            return
        }
        val data = getItem(position)!!
        holder.view.apply {
            findViewById<TextView>(R.id.homeRvTitle).text = data.title.htmlDecode()
            findViewById<TextView>(R.id.homeRvDate).text = data.niceDate
            findViewById<TextView>(R.id.homeRvHead).text =
                if (data.author.isEmpty()) data.shareUser else data.author
            findViewById<TextView>(R.id.homeRvLabel).text =
                data.superChapterName + "/" + data.chapterName
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
            return Holder(HomeVpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply { headerBinding = this }.root)
        }
        return Holder(HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .apply { lastItemBinding = this }.root)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 0
        return 1
    }

    fun getItemOut(position: Int): ArticleData? = getItem(position)

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            if (view !is ViewPager2) {
                view.setOnClickListener {
                    val data = getItem(absoluteAdapterPosition)!!
                    WebPageActivity.startActivity(context, data.link, data.title)
                }
                callback.onCollect(this, this@HomePagingDataAdapter, context)
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

    interface CallBack {

        val onBindHeader: HomeVpBinding.() -> Unit

        val onCollect: Holder.(HomePagingDataAdapter, Context) -> Unit

    }
}