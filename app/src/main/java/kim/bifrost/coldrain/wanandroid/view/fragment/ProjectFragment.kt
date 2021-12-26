package kim.bifrost.coldrain.wanandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentProjectBinding
import kim.bifrost.coldrain.wanandroid.view.adapter.StandardPagerAdapter
import kim.bifrost.coldrain.wanandroid.view.fragment.inner.InnerProjectFragment
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.ProjectFragViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectFragment : BaseVMFragment<ProjectFragViewModel, FragmentProjectBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentProjectBinding = FragmentProjectBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.types.collectLatest {
                it.ifSuccess { types ->
                    binding.vp2Project.adapter = StandardPagerAdapter(childFragmentManager, lifecycle, types!!) { data, pos ->
                        InnerProjectFragment.getInstance(data[pos].id)
                    }
                    TabLayoutMediator(binding.tlProject, binding.vp2Project) { tab, i ->
                        tab.text = types[i].name
                    }.attach()
                }
            }
        }
    }

    override fun scrollToTop() {
        (childFragmentManager.findFragmentByTag("f${binding.vp2Project.currentItem}") as BaseVMFragment<*,*>).scrollToTop()
    }
}