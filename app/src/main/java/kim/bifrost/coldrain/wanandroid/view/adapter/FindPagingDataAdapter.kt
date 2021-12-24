package kim.bifrost.coldrain.wanandroid.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.PagerArticlesData
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.FindPagingDataAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/18 18:33
 **/
class FindPagingDataAdapter(context: Context, private val callback: Holder<HomeRvItemBinding>.(FindPagingDataAdapter) -> Unit) : BasePagingAdapter<HomeRvItemBinding, ArticleData>(context) {
    override fun getDataBinding(parent: ViewGroup, viewType: Int): HomeRvItemBinding =
        HomeRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder<HomeRvItemBinding>, position: Int) {
        holder.binding.apply {
            val data = getItem(position)!!
            homeRvTitle.text = data.title
            homeRvDate.text = data.niceDate
            homeRvHead.text = if (data.author.isNotEmpty()) data.author else data.shareUser
            homeRvLabel.text = data.superChapterName + "/" + data.chapterName
            homeButtonLike.apply {
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

    override val holderInit: Holder<HomeRvItemBinding>.() -> Unit
        get() = {
            callback(this, this@FindPagingDataAdapter)
            binding.root.setOnClickListener {
                // 点击进入网页
                val data = getItem(bindingAdapterPosition)
                WebPageActivity.startActivity(context, data!!.link, data.title)
            }
        }
}