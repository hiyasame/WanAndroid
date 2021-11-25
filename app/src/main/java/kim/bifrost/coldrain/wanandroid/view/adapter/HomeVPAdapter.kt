package kim.bifrost.coldrain.wanandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

    private var itemClickListener: (Int) -> Unit = { }

    inner class Holder(val binding: HomeVpItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(HomeVpItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = getItems()[position]
        // 使用Glide加载网络图片
        Glide.with(holder.itemView)
            .load(data.imagePath)
            .into(holder.binding.homeVpImg)
        holder.binding.homeVpText.text = data.desc
    }

    override fun getItemCount(): Int = getItems().size

    fun setOnItemClickListener(func: (Int) -> Unit) {
        itemClickListener = func
    }
}