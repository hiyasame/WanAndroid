package kim.bifrost.coldrain.wanandroid.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.databinding.CollectRvItemBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.CollectionData
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.CollectRVAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/30 19:17
 **/
class CollectPagingDataAdapter(val context: Context) :
    PagingDataAdapter<CollectionData.SingleCollectionData, CollectPagingDataAdapter.Holder>(
        CollectRvItemCallBack()) {

    inner class Holder(val binding: CollectRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(CollectRvItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val data = getItem(position)
            author.text = data!!.author
            time.text = data.niceDate
            type.text = data.chapterName
            title.text = data.title
        }
    }

    // 收藏或不收藏
    suspend fun postUnCollect(id: Int) {
        ApiService.uncollect(id).ifSuccess {
            toastConcurrent("取消收藏成功")
            withContext(Dispatchers.Main) {
                refresh()
            }
        }.ifFailure {
            toastConcurrent("取消收藏失败: $it")
        }
    }

    class CollectRvItemCallBack : DiffUtil.ItemCallback<CollectionData.SingleCollectionData>() {
        override fun areItemsTheSame(
            oldItem: CollectionData.SingleCollectionData,
            newItem: CollectionData.SingleCollectionData,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CollectionData.SingleCollectionData,
            newItem: CollectionData.SingleCollectionData,
        ): Boolean {
            return oldItem == newItem
        }
    }

}