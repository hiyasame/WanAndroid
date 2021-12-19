package kim.bifrost.coldrain.wanandroid.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.WxArticleType
import kim.bifrost.coldrain.wanandroid.view.fragment.InnerWechatFragment

/**
 * kim.bifrost.coldrain.wanandroid.view.adapter.WechatPagerAdapter
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/19 21:30
 **/
class WechatPagerAdapter(fragment: Fragment, private val types: List<WxArticleType>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = types.size

    override fun createFragment(position: Int): Fragment {
        return InnerWechatFragment.getInstance(types[position].id)
    }
}