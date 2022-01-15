package kim.bifrost.coldrain.wanandroid.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.base.BaseVMActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivitySearchResultBinding
import kim.bifrost.coldrain.wanandroid.utils.getCollectFunc
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.view.adapter.FindPagingDataAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.SearchResultViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchResultActivity : BaseVMActivity<SearchResultViewModel, ActivitySearchResultBinding>() {

    override val viewModelFactory: ViewModelProvider.Factory
        get() = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return modelClass
                    .getConstructor(String::class.java)
                    .newInstance(intent.getStringExtra("query"))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.mainToolbar.toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = viewModel.key
        }
        val adapter = FindPagingDataAdapter(this, getCollectFunc(lifecycleScope))
        binding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            this.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.pageList.collectLatest {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Error -> {
                    toast("发生错误: ${(it.refresh as LoadState.Error).error.message}")
                    Log.d("Test", "(SearchResultActivity.kt:60) ==> ${(it.refresh as LoadState.Error).error.message}")
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

    override fun getViewBinding(): ActivitySearchResultBinding = ActivitySearchResultBinding.inflate(layoutInflater)

    companion object {
        fun start(context: Context, query: String) {
            val starter = Intent(context, SearchResultActivity::class.java)
                .putExtra("query", query)
            context.startActivity(starter)
        }
    }
}