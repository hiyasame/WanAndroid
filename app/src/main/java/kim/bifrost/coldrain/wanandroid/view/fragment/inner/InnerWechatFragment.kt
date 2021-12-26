package kim.bifrost.coldrain.wanandroid.view.fragment.inner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.base.BaseVMFragment
import kim.bifrost.coldrain.wanandroid.databinding.FragmentInnerWechatBinding
import kim.bifrost.coldrain.wanandroid.utils.scrollToTop
import kim.bifrost.coldrain.wanandroid.view.adapter.InnerWechatPagingDataAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.InnerWechatFragViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * kim.bifrost.coldrain.wanandroid.view.fragment.inner.InnerWechatFragment
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/19 21:38
 **/
class InnerWechatFragment : BaseVMFragment<InnerWechatFragViewModel, FragmentInnerWechatBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cid = arguments?.getInt("cid") ?: error("Inner wechat fragment without cid")
        val adapter = InnerWechatPagingDataAdapter(requireContext(), viewModel.collectCallback)
        binding.rvInnerWechat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInnerWechat.adapter = adapter
        lifecycleScope.launch {
            viewModel.getHistoryArticleList(cid).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        boolean: Boolean,
    ): FragmentInnerWechatBinding = FragmentInnerWechatBinding.inflate(inflater, container, boolean)

    override fun scrollToTop() {
        binding.rvInnerWechat.scrollToTop()
    }

    companion object {
        fun getInstance(cid: Int): InnerWechatFragment {
            return InnerWechatFragment().apply {
                arguments = Bundle().also { it.putInt("cid", cid) }
            }
        }
    }
}