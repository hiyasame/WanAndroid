package kim.bifrost.coldrain.wanandroid.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.base.BaseVMActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivityCollectBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.adapter.CollectPagingDataAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.CollectViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectActivity : BaseVMActivity<CollectViewModel, ActivityCollectBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.collectToolbar.toolbar.apply {
            setSupportActionBar(this)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.title = "收藏"
            }
        }
        val adapter = CollectPagingDataAdapter(this) {
            viewModel.uncollect(it) {
                refresh()
            }
        }
        binding.rvCollect.apply {
            layoutManager = LinearLayoutManager(this@CollectActivity)
            this.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.collections.collectLatest {
                adapter.submitData(it)
            }
        }
        // 刷新
        binding.srlCollect.setOnRefreshListener {
            adapter.refresh()
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.srlCollect.isRefreshing = true
                }
                is LoadState.NotLoading -> {
                    binding.srlCollect.isRefreshing = false
                    // 没有元素时显示没有元素的图标
                    binding.noCollectLayout.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
                }
                is LoadState.Error -> {
                    (it.refresh as LoadState.Error).error.printStackTrace()
                    toast("发生错误")
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun getViewBinding(): ActivityCollectBinding = ActivityCollectBinding.inflate(layoutInflater)
}