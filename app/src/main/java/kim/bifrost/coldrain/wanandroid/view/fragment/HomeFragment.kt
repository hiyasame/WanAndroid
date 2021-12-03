package kim.bifrost.coldrain.wanandroid.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.databinding.FragmentHomeBinding
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.view.activity.WebPageActivity
import kim.bifrost.coldrain.wanandroid.view.adapter.HomePagingDataAdapter
import kim.bifrost.coldrain.wanandroid.view.adapter.HomeVPAdapter
import kim.bifrost.coldrain.wanandroid.viewmodel.frag.HomeFragViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val vpLooper = object : Runnable {
        override fun run() {
            // 控制滑动速度
            App.coroutineScope.launch {
                if (binding.vpHome.scrollState == ViewPager2.SCROLL_STATE_IDLE) {
                    binding.vpHome.beginFakeDrag()
                    for (i in 1..40) {
                        delay(15)
                        binding.vpHome.fakeDragBy(-binding.vpHome.width.toFloat() / 40)
                    }
                    binding.vpHome.endFakeDrag()
                }
            }
            binding.vpHome.postDelayed(this, 5000)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = ViewModelProvider(this)[HomeFragViewModel::class.java]
        // 在网络操作完毕后刷新ViewPager页面显示
        binding.viewModel?.updater?.observe(viewLifecycleOwner) {
            // 设置一个比较靠前的位置使其可以向前滑动
            binding.vpHome.endFakeDrag()
            binding.vpHome.currentItem = 4000
            (binding.vpHome.adapter as HomeVPAdapter).flush()
            binding.srlHome.isRefreshing = false
            binding.vpHome.adapter?.notifyDataSetChanged()
        }
        binding.vpHome.adapter = HomeVPAdapter { binding.viewModel!!.banners }.apply {
            setOnItemClickListener {
                val data = getItems()[it]
                WebPageActivity.startActivity(requireContext(), data.url, data.title)
            }
        }
        // 轮播
        binding.vpHome.postDelayed(vpLooper, 5000)
        // 未加载完毕
        binding.srlHome.isRefreshing = true
        // 进行网络操作
        binding.viewModel?.refresh()
        // 下滑刷新操作
        val adapter = HomePagingDataAdapter(requireContext())
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        lifecycleScope.launch {
            binding.viewModel?.articleList?.collectLatest {
                adapter.submitData(it)
            }
        }

        binding.srlHome.setOnRefreshListener {
            // 重新加载
            binding.viewModel?.refresh()
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
}