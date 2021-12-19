package kim.bifrost.coldrain.wanandroid.base

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/4 11:36
 **/
abstract class BasePagingAdapter<DB : ViewDataBinding, D : Any>(
    val context: Context,
    callback: BaseItemCallBack<D> = BaseItemCallBack(),
) :
    PagingDataAdapter<D, BasePagingAdapter.Holder<DB>>(callback) {

    open val holderInit: Holder<DB>.() -> Unit = {}

    class Holder<DB : ViewDataBinding>(val binding: DB, holderInit: Holder<DB>.() -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            holderInit(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<DB> {
        return Holder(getDataBinding(parent, viewType), holderInit)
    }

    abstract fun getDataBinding(parent: ViewGroup, viewType: Int): DB

    fun getItemOut(position: Int): D? {
        return getItem(position)
    }
}