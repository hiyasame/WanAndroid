package kim.bifrost.coldrain.wanandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentSystemBinding
import kim.bifrost.coldrain.wanandroid.view.adapter.StandardPagerAdapter
import kim.bifrost.coldrain.wanandroid.view.fragment.inner.InnerNavigationFragment
import kim.bifrost.coldrain.wanandroid.view.fragment.inner.InnerSystemFragment
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerSystemFragViewModel
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.SystemFragViewModel


class SystemFragment : BaseVMFragment<SystemFragViewModel, FragmentSystemBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentSystemBinding = FragmentSystemBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val types = listOf("体系", "导航")
        binding.vp2System.adapter = StandardPagerAdapter(childFragmentManager, lifecycle, types) { _, i ->
            when (i) {
                0 -> InnerSystemFragment()
                1 -> InnerNavigationFragment()
                else -> error("wrong state")
            }
        }
        TabLayoutMediator(binding.tlSystem, binding.vp2System) { tab, i ->
            tab.text = types[i]
        }.attach()
    }

    override fun scrollToTop() {
        (childFragmentManager.findFragmentByTag("f${binding.vp2System.currentItem}") as BaseVMFragment<*,*>).scrollToTop()
    }
}