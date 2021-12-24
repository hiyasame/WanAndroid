package kim.bifrost.coldrain.wanandroid.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
import kim.bifrost.coldrain.wanandroid.databinding.ItemRecyclerViewBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.SystemData
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.RVItemPagerAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/22 18:35
 **/
class RVItemPagerAdapter(private val context: Context, private val coroutineScope: CoroutineScope, private val data: List<SystemData>, private val callback: CallBack) : RecyclerView.Adapter<RVItemPagerAdapter.Holder>() {
    inner class Holder(val binding: ItemRecyclerViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.rvItem.apply {
            val adapter = InnerRvPagingAdapter(this@RVItemPagerAdapter.context, callback.onCollect)
            layoutManager = LinearLayoutManager(this@RVItemPagerAdapter.context)
            this.adapter = adapter
            coroutineScope.launch {
                callback.collectData(data[position].id).collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    interface CallBack {
        val onCollect: BasePagingAdapter.Holder<HomeRvItemBinding>.(InnerRvPagingAdapter) -> Unit
        suspend fun collectData(cid: Int): Flow<PagingData<ArticleData>>
    }
}

class InnerRvPagingAdapter(context: Context, private val callback: Holder<HomeRvItemBinding>.(InnerRvPagingAdapter) -> Unit) : BasePagingAdapter<HomeRvItemBinding, ArticleData>(context) {
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
            callback(this, this@InnerRvPagingAdapter)
            binding.root.setOnClickListener {
                // 点击进入网页
                val data = getItem(bindingAdapterPosition)
                WebPageActivity.startActivity(context, data!!.link, data.title)
            }
        }
}