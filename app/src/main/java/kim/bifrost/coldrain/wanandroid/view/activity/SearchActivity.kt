package kim.bifrost.coldrain.wanandroid.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BaseVMActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivitySearchBinding
import kim.bifrost.coldrain.wanandroid.utils.tag
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.adapter.SearchHistoryAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

class SearchActivity : BaseVMActivity<SearchViewModel, ActivitySearchBinding>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.searchView.apply {
            isSubmitButtonEnabled = true
            isQueryRefinementEnabled = true
            setIconifiedByDefault(false)
            //隐藏icon
            findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
                .apply {
                    setImageDrawable(null)
                    visibility = View.GONE
                }
            queryHint = "发现更多干货"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isEmpty()) return false
                    App.searchHistoryData.edit {
                        putStringSet(
                            "search_history",
                            App.searchHistoryData
                                .getStringSet("search_history", emptySet())!!
                                .toMutableSet()
                                .apply {
                                    add(query)
                                }
                        )
                    }
                    SearchResultActivity.start(this@SearchActivity, query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean = true
            })
        }
        lifecycleScope.launch {
            // TagLayout
            binding.tlHot.apply {
                viewModel.getHotKeys().ifSuccess {
                    it!!.forEach { data ->
                        addView(
                            tag(
                                context,
                                data.name,
                                Color.valueOf(Color.DKGRAY)
                            ) { view -> binding.searchView.setQuery((view as TextView).text, false) }
                        )
                    }
                }.ifFailure {
                    toastConcurrent("网络请求失败: $it")
                }
            }
        }
        binding.rvSearchHistory.layoutManager = LinearLayoutManager(this)
        refreshHistoryRv()
        binding.tvRemoveAll.setOnClickListener {
            App.searchHistoryData.edit {
                putStringSet("search_history", null)
            }
            refreshHistoryRv()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun getViewBinding(): ActivitySearchBinding = ActivitySearchBinding.inflate(layoutInflater)

    private fun refreshHistoryRv() {
        App.searchHistoryData.getStringSet("search_history", emptySet())!!
            .toMutableList()
            .also {
                binding.rvSearchHistory
                    .adapter = SearchHistoryAdapter(it, binding.searchView) { i ->
                    it.removeAt(i)
                    App.searchHistoryData.edit {
                        putStringSet("search_history", it.toSet())
                    }
                    refreshHistoryRv()
                }
            }
    }
}