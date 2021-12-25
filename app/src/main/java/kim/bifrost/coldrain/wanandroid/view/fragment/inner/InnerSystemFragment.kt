package kim.bifrost.coldrain.wanandroid.view.fragment.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentInnerSystemBinding
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.adapter.SystemDataAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerSystemFragViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.delay


/**
 * kim.bifrost.coldrain.wanandroid.view.fragment.inner.InnerSystemFragment
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/20 19:27
 **/
class InnerSystemFragment : BaseVMFragment<InnerSystemFragViewModel, FragmentInnerSystemBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentInnerSystemBinding = FragmentInnerSystemBinding.inflate(inflater, container, boolean)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.srlInnerSystem.isRefreshing = true
        lifecycleScope.launch {
            viewModel.systemData.collectLatest {
                it.ifSuccess {
                    binding.rvInnerSystem.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = SystemDataAdapter(requireContext(), it!!)
                        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    }
                    binding.srlInnerSystem.isRefreshing = false
                }.ifFailure { s ->
                    toastConcurrent(s)
                }
            }
        }
        binding.srlInnerSystem.setOnRefreshListener {
            lifecycleScope.launch {
                delay(500L)
                binding.srlInnerSystem.isRefreshing = false
            }
        }
    }

    override fun scrollToTop() {
        binding.rvInnerSystem.run {
            if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }
}