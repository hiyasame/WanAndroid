package kim.bifrost.coldrain.wanandroid.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.StandardPagerAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/20 19:08
 **/
class StandardPagerAdapter<D: Any>(fragment: FragmentManager, lifecycle: Lifecycle, private val types: List<D>, private val createFragmentFunc: (List<D>, Int) -> Fragment) : FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int = types.size

    override fun createFragment(position: Int): Fragment {
        return createFragmentFunc(types, position)
    }
}