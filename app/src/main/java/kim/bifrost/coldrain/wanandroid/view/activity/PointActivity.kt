package kim.bifrost.coldrain.wanandroid.view.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.base.BaseVMActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivityPointBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.NetResponse
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.adapter.PointRVAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.PointViewModel
import kotlinx.coroutines.launch

class PointActivity : BaseVMActivity<PointViewModel, ActivityPointBinding>() {

    override fun getViewBinding(): ActivityPointBinding = ActivityPointBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.title = "积分明细"
            }
        }
        binding.rvPoint.apply {
            layoutManager = LinearLayoutManager(context)
            lifecycleScope.launch {
                val netResponses = arrayListOf<NetResponse<*>>()
                viewModel.getPointChangeList().ifSuccess {
                    viewModel.getPointData().ifSuccess { data ->
                        adapter = PointRVAdapter(data!!.coinCount, it!!)
                    }.also { netResponses.add(it) }
                }.also { netResponses.add(it) }
                netResponses.forEach {
                    it.ifFailure { s ->
                        toastConcurrent("错误: $s")
                    }
                }
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}