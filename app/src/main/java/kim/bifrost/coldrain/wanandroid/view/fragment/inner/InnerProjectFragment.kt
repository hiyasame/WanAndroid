package kim.bifrost.coldrain.wanandroid.view.fragment.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentInnerProjectBinding
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.view.adapter.InnerProjectRvAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerProjectFragViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * kim.bifrost.coldrain.wanandroid.view.fragment.inner.InnerProjectFragment
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/25 20:49
 **/
class InnerProjectFragment : BaseVMFragment<InnerProjectFragViewModel, FragmentInnerProjectBinding>() {

    private val cid by lazy { requireArguments().getInt("id") }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentInnerProjectBinding = FragmentInnerProjectBinding.inflate(inflater, container, boolean)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = InnerProjectRvAdapter(requireContext(), viewModel.onCollect)
        binding.rvInnerProject.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.getProjectArticles(cid).collectLatest {
                adapter.submitData(it)
            }
        }
        binding.srlInnerProject.setOnRefreshListener {
            adapter.refresh()
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.srlInnerProject.isRefreshing = true
                }
                is LoadState.NotLoading -> {
                    binding.srlInnerProject.isRefreshing = false
                }
                is LoadState.Error -> {
                    toast("发生错误")
                }
            }
        }
    }

    companion object {
        fun getInstance(cid: Int): InnerProjectFragment {
            return InnerProjectFragment().apply {
                arguments = Bundle().also { it.putInt("id", cid) }
            }
        }
    }
}