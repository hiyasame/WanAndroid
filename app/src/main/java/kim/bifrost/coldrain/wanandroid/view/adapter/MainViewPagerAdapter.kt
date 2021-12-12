package kim.bifrost.coldrain.wanandroid.view.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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
            0 -> activity.supportFragmentManager.findFragmentByTag("f0") ?: HomeFragment()
            1 -> activity.supportFragmentManager.findFragmentByTag("f1") ?: FindFragment()
            2 -> activity.supportFragmentManager.findFragmentByTag("f2") ?: WechatFragment()
            3 -> activity.supportFragmentManager.findFragmentByTag("f3") ?: SystemFragment()
            4 -> activity.supportFragmentManager.findFragmentByTag("f4") ?: ProjectFragment()
            else -> error("wrong state")
        }
    }
}