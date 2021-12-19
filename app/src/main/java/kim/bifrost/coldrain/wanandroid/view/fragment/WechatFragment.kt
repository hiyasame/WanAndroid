package kim.bifrost.coldrain.wanandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import kim.bifrost.coldrain.wanandroid.base.BaseFragment
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentWechatBinding
import kim.bifrost.coldrain.wanandroid.view.adapter.WechatPagerAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.WechatFragViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WechatFragment : BaseVMFragment<WechatFragViewModel, FragmentWechatBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentWechatBinding = FragmentWechatBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.wechatArticleTypes.collectLatest { types ->
                binding.vp2Wechat.adapter = WechatPagerAdapter(this@WechatFragment, types)
                TabLayoutMediator(binding.tlWechat, binding.vp2Wechat) { tab, i ->
                    tab.text = types[i].name
                }.attach()
            }
        }
    }

    override fun scrollToTop() {
        (childFragmentManager.findFragmentByTag("f${binding.vp2Wechat.currentItem}") as InnerWechatFragment).scrollToTop()
    }
}