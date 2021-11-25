package kim.bifrost.coldrain.wanandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kim.bifrost.coldrain.wanandroid.databinding.FragmentHomeBinding
import kim.bifrost.coldrain.wanandroid.view.adapter.HomeVPAdapter
import kim.bifrost.coldrain.wanandroid.viewmodel.frag.HomeFragViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = ViewModelProvider(this)[HomeFragViewModel::class.java]
        binding.vpHome.adapter = HomeVPAdapter { binding.viewModel!!.banners }
    }
}