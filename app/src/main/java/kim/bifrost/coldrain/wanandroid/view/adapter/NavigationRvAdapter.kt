package kim.bifrost.coldrain.wanandroid.view.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kim.bifrost.coldrain.wanandroid.databinding.ItemNavigationListBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.NavigationData
import kim.bifrost.coldrain.wanandroid.utils.getMarginLayoutParams
import kim.bifrost.coldrain.wanandroid.utils.htmlDecode
import kim.bifrost.coldrain.wanandroid.utils.randomColor
import kim.bifrost.coldrain.wanandroid.utils.tag
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.NavigationRvAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/21 0:28
 **/
class NavigationRvAdapter(private val context: Context, private val data: List<NavigationData>) : RecyclerView.Adapter<NavigationRvAdapter.Holder>() {

    inner class Holder(val binding: ItemNavigationListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemNavigationListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = data[position]
        holder.binding.apply {
            title.text = data.name.htmlDecode()
            data.articles.forEach {
                val tag = tagLayout.tag(
                    context,
                    it.title,
                    randomColor()
                ) { _ ->
                    WebPageActivity.startActivity(context, it.link, it.title)
                }
                tagLayout.addView(tag)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}