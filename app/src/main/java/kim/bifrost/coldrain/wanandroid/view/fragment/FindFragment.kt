package kim.bifrost.coldrain.wanandroid.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import kim.bifrost.coldrain.wanandroid.databinding.FragmentFindBinding
import kim.bifrost.coldrain.wanandroid.databinding.FragmentHomeBinding


class FindFragment : BaseFragment<FragmentFindBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean
    ): FragmentFindBinding = FragmentFindBinding.inflate(inflater, container, false)
}