package kim.bifrost.coldrain.wanandroid.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kim.bifrost.coldrain.wanandroid.view.FragmentRegistry
import kim.bifrost.coldrain.wanandroid.view.fragment.*

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.MainViewPagerAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/24 17:07
 **/
class MainViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        val target = when (position) {
            0 -> HomeFragment::class.java
            1 -> FindFragment::class.java
            2 -> WechatFragment::class.java
            3 -> SystemFragment::class.java
            4 -> ProjectFragment::class.java
            else -> error("wrong state")
        }
        return FragmentRegistry.getInstance(target)
    }
}