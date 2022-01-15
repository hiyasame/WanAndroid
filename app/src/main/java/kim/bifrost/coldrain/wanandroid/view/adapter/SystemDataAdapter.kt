package kim.bifrost.coldrain.wanandroid.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.databinding.ItemRvInnerSystemBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.SystemData
import kim.bifrost.coldrain.wanandroid.utils.htmlDecode
import kim.bifrost.coldrain.wanandroid.view.activity.InnerSystemActivity
import java.lang.StringBuilder

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.SystemDataAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/20 19:55
 **/
class SystemDataAdapter(private val context: Context, private val data: List<SystemData>) : RecyclerView.Adapter<SystemDataAdapter.Holder>() {
    inner class Holder(val binding: ItemRvInnerSystemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                InnerSystemActivity.startActivity(context, data[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemRvInnerSystemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = data[position]
        val children = data.children
        holder.binding.apply {
            title.text = data.name.htmlDecode()
            content.text = StringBuilder().apply {
                val iter = children.iterator()
                while (iter.hasNext()) {
                    append(iter.next().name)
                    if (iter.hasNext()) {
                        append("   ")
                    }
                }
            }.toString()
        }
    }

    override fun getItemCount(): Int = data.size
}