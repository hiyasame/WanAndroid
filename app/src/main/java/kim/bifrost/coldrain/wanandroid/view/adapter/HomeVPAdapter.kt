package kim.bifrost.coldrain.wanandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kim.bifrost.coldrain.wanandroid.databinding.HomeVpItemBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.BannerData

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.HomeVPAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/25 19:39
 **/
class HomeVPAdapter(
    val getItems: () -> List<BannerData>
) : RecyclerView.Adapter<HomeVPAdapter.Holder>() {

    val items by lazy { getItems().toMutableList() }

    private var itemClickListener: (Int) -> Unit = { }

    inner class Holder(val binding: HomeVpItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener(absoluteAdapterPosition % items.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(HomeVpItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = items[position % items.size]
        // 使用Glide加载网络图片
        Glide.with(holder.itemView)
            .load(data.imagePath)
            .into(holder.binding.homeVpImg)
        holder.binding.homeVpText.text = data.title
    }

    override fun getItemCount(): Int = if (items.isEmpty()) 0 else Integer.MAX_VALUE

    fun setOnItemClickListener(func: (Int) -> Unit) {
        itemClickListener = func
    }

    fun flush() {
        items.apply {
            clear()
            addAll(getItems())
        }
    }
}