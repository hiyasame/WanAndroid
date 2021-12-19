package kim.bifrost.coldrain.wanandroid.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentHomeBinding
import kim.bifrost.coldrain.wanandroid.databinding.HomeVpBinding
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity
import kim.bifrost.coldrain.wanandroid.view.adapter.HomePagingDataAdapter
import kim.bifrost.coldrain.wanandroid.view.adapter.HomeVPAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.HomeFragViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseVMFragment<HomeFragViewModel, FragmentHomeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 未加载完毕
        binding.srlHome.isRefreshing = true
        // 进行网络操作
        viewModel.refresh()
        val adapter =
            HomePagingDataAdapter(requireContext(), object : HomePagingDataAdapter.CallBack {
                override val onBindHeader: HomeVpBinding.() -> Unit = {
                    val vpLooper = object : Runnable {
                        override fun run() {
                            // 控制滑动速度
                            lifecycleScope.launch {
                                if (vpHome.scrollState == ViewPager2.SCROLL_STATE_IDLE) {
                                    vpHome.beginFakeDrag()
                                    for (i in 1..40) {
                                        delay(15)
                                        vpHome.fakeDragBy(-vpHome.width.toFloat() / 40)
                                    }
                                    vpHome.endFakeDrag()
                                }
                            }
                            vpHome.postDelayed(this, 5000)
                        }
                    }
                    vpHome.adapter = HomeVPAdapter { viewModel.banners }.apply {
                        setOnItemClickListener {
                            val data = getItems()[it]
                            WebPageActivity.startActivity(requireContext(), data.url, data.title)
                        }
                    }
                    // 在网络操作完毕后刷新ViewPager页面显示
                    viewModel.updater.observe(viewLifecycleOwner) {
                        // 设置一个比较靠前的位置使其可以向前滑动
                        vpHome.endFakeDrag()
                        vpHome.currentItem = 4000
                        (vpHome.adapter as HomeVPAdapter).flush()
                        binding.srlHome.isRefreshing = false
                        vpHome.adapter?.notifyDataSetChanged()
                    }
                    // 轮播
                    vpHome.postDelayed(vpLooper, 5000)
                }

                override val onCollect: HomePagingDataAdapter.Holder.(HomePagingDataAdapter, Context) -> Unit
                    get() = viewModel.onCollect

            })
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.articleList.collectLatest {
                adapter.submitData(it)
            }
        }
        // 下滑刷新操作
        binding.srlHome.setOnRefreshListener {
            // 重新加载
            viewModel.refresh()
            adapter.refresh()
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.srlHome.isRefreshing = true
                }
                is LoadState.NotLoading -> {
                    binding.srlHome.isRefreshing = false
                }
                is LoadState.Error -> {
                    toast("发生错误")
                }
            }
        }
    }

    override fun scrollToTop() {
        binding.rvHome.run {
            if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }
}