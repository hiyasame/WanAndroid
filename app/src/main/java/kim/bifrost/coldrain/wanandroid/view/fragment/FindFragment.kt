package kim.bifrost.coldrain.wanandroid.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.base.BaseFragment
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentFindBinding
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.view.adapter.FindPagingDataAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.FindFragViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FindFragment : BaseVMFragment<FindFragViewModel, FragmentFindBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentFindBinding = FragmentFindBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 未加载完毕
        binding.srlFind.isRefreshing = true
        val adapter = FindPagingDataAdapter(requireContext(), viewModel.collectCallback)
        binding.rvFind.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.squareDataList.collectLatest {
                adapter.submitData(it)
            }
        }
        binding.srlFind.setOnRefreshListener {
            adapter.refresh()
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.srlFind.isRefreshing = true
                }
                is LoadState.NotLoading -> {
                    binding.srlFind.isRefreshing = false
                }
                is LoadState.Error -> {
                    toast("发生错误")
                }
            }
        }
    }

    override fun scrollToTop() {
        binding.rvFind.run {
            if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }
}