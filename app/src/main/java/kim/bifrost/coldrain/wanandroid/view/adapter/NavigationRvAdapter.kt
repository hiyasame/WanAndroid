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
import kim.bifrost.coldrain.wanandroid.utils.randomColor
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
            title.text = data.name
            data.articles.forEach {
                val tv = TextView(context).apply {
                    setPadding(30)
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                    text = it.title
                    layoutParams = (tagLayout.layoutParams as ViewGroup.MarginLayoutParams).apply { setMargins(15, 10, 15, 10) }
                    setBackgroundColor(Color.parseColor("#CBEDEDED"))
                    setTextColor(randomColor().toArgb())
                    setOnClickListener { _ ->
                        WebPageActivity.startActivity(context, it.link, it.title)
                    }
                }
                tagLayout.addView(tv)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}