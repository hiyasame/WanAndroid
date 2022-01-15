package kim.bifrost.coldrain.wanandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import kim.bifrost.coldrain.wanandroid.databinding.ItemSearchHistoryBinding

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.SearhHistoryAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2022/1/14 20:53
 **/
class SearchHistoryAdapter(
    private val historySet: List<String>,
    private val searchView: SearchView,
    private val removeHistoryCallback: (Int) -> Unit
) : RecyclerView.Adapter<SearchHistoryAdapter.Holder>() {
    inner class Holder(val binding: ItemSearchHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                searchView.setQuery(historySet[bindingAdapterPosition], false)
            }
            binding.remove.setOnClickListener {
                removeHistoryCallback(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemSearchHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            tv.text = historySet[position]
        }
    }

    override fun getItemCount(): Int = historySet.size

}