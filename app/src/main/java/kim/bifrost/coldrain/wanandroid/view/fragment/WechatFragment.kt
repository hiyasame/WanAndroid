package kim.bifrost.coldrain.wanandroid.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import kim.bifrost.coldrain.wanandroid.base.BaseFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentWechatBinding


class WechatFragment : BaseFragment<FragmentWechatBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean
    ): FragmentWechatBinding = FragmentWechatBinding.inflate(inflater, container, false)
}