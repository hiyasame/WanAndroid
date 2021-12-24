package kim.bifrost.coldrain.wanandroid.view.fragment.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentInnerNavigationBinding
import kim.bifrost.coldrain.wanandroid.view.adapter.NavigationRvAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerNavigationFragViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.ITabView
import q.rorbin.verticaltablayout.widget.QTabView
import q.rorbin.verticaltablayout.widget.TabView

/**
 * kim.bifrost.coldrain.wanandroid.view.fragment.inner.InnerNavigationFragment
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/20 21:12
 **/
class InnerNavigationFragment :
    BaseVMFragment<InnerNavigationFragViewModel, FragmentInnerNavigationBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentInnerNavigationBinding = FragmentInnerNavigationBinding.inflate(inflater, container, boolean)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleScope.launch {
                viewModel.naviData.collectLatest {
                    it.ifSuccess { list ->
                        rvNavigation.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = NavigationRvAdapter(context, list)
                        }
                        list.forEach { d ->
                            vtlNavigation.addTab(QTabView(context).apply {
                                title = ITabView.TabTitle.Builder()
                                    .setTextSize(18)
                                    .setContent(d.name)
                                    .build()
                            })
                        }
                    }
                }
            }
            vtlNavigation.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabView?, position: Int) {
                }

                override fun onTabSelected(tab: TabView?, position: Int) {
                    scrollTo(position)
                }
            })
        }
    }

    private fun scrollTo(pos: Int) {
        binding.rvNavigation.stopScroll()
        binding.rvNavigation.scrollToPosition(pos)
    }

    override fun scrollToTop() {
        binding.vtlNavigation.setTabSelected(0)
        binding.rvNavigation.run {
            if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }
}