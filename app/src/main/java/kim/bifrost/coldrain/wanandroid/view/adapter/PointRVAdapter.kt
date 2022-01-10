package kim.bifrost.coldrain.wanandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.databinding.ItemPointCommonBinding
import kim.bifrost.coldrain.wanandroid.databinding.ItemPointHeaderBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.PagerData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.PointChangeData

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.PointRVAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2022/1/10 20:43
 **/
class PointRVAdapter(private val score: Int, val data: PagerData<PointChangeData>) : RecyclerView.Adapter<PointRVAdapter.Holder>() {
    inner class Holder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = if (viewType == 0)
            ItemPointCommonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        else
            ItemPointHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (position == 0) {
            val binding = holder.binding as ItemPointHeaderBinding
            binding.score.text = score.toString()
            return
        }
        val binding = holder.binding as ItemPointCommonBinding
        val data = data.datas[position - 1]
        binding.coin.text = data.coinCount.toString()
        binding.desc.text = data.desc
        binding.reason.text = data.reason
    }

    override fun getItemCount(): Int = data.datas.size

    override fun getItemViewType(position: Int): Int = if (position == 0) 1 else 0
}