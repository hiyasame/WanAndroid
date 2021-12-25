package kim.bifrost.coldrain.wanandroid.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kim.bifrost.coldrain.wanandroid.view.fragment.*

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.MainViewPagerAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/11/24 17:07
 **/
class MainViewPagerAdapter(val activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> FindFragment()
            2 -> WechatFragment()
            3 -> SystemFragment()
            4 -> ProjectFragment()
            else -> error("wrong state")
        }
    }
}