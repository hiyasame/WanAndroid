package kim.bifrost.coldrain.wanandroid.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.databinding.CollectRvItemBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.CollectionData
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.CollectRVAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/30 19:17
 **/
class CollectPagingDataAdapter(context: Context, val postUnCollect: CollectPagingDataAdapter.(Int) -> Unit) :
    BasePagingAdapter<CollectRvItemBinding, CollectionData.SingleCollectionData>(context) {

    override val holderInit: Holder<CollectRvItemBinding>.() -> Unit
        get() = {
            binding.root.setOnClickListener {
                val data = getItem(absoluteAdapterPosition)!!
                WebPageActivity.startActivity(context, data.link, data.title)
            }
            binding.collect.setOnClickListener {
                val data = getItem(absoluteAdapterPosition)!!
                App.coroutineScope.launch(Dispatchers.IO) {
                    postUnCollect(data.id)
                }
            }
        }

    override fun onBindViewHolder(
        holder: Holder<CollectRvItemBinding>,
        position: Int,
    ) {
        holder.binding.apply {
            val data = getItem(position)
            author.text = data!!.author
            time.text = data.niceDate
            type.text = data.chapterName
            title.text = data.title
        }
    }

    override fun getDataBinding(parent: ViewGroup, viewType: Int): CollectRvItemBinding =
        CollectRvItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)

}