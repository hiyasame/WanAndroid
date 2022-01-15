package kim.bifrost.coldrain.wanandroid.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.databinding.ItemInnerProjectBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.utils.htmlDecode
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.InnerProjectRvAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/25 22:11
 **/
class InnerProjectRvAdapter(context: Context, private val callback: Holder<ItemInnerProjectBinding>.(InnerProjectRvAdapter, View) -> Unit) : BasePagingAdapter<ItemInnerProjectBinding, ArticleData>(context) {
    override fun getDataBinding(parent: ViewGroup, viewType: Int): ItemInnerProjectBinding =
        ItemInnerProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBindViewHolder(holder: Holder<ItemInnerProjectBinding>, position: Int) {
        val data = getItem(position)
        holder.binding.apply {
            author.text = if (data!!.author.isNotEmpty()) data.author else data.shareUser
            content.text = data.desc
            date.text = data.niceDate
            Glide.with(image)
                .load(data.envelopePic)
                .into(image)
            title.text = data.title.htmlDecode()
            if (data.collect) {
                collectButton.setImageResource(R.drawable.ic_like)
                collectButton.setColorFilter(Color.parseColor("#CDF68A8A"))
            }
        }
    }

    override val holderInit: Holder<ItemInnerProjectBinding>.() -> Unit
        get() = {
            callback(this, this@InnerProjectRvAdapter, binding.collectButton)
            binding.root.setOnClickListener {
                // 点击进入网页
                val data = getItem(bindingAdapterPosition)
                WebPageActivity.startActivity(context, data!!.link, data.title)
            }
        }
}