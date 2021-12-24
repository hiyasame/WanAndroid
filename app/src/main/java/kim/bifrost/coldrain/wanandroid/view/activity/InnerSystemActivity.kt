package kim.bifrost.coldrain.wanandroid.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.google.android.material.tabs.TabLayoutMediator
import kim.bifrost.coldrain.wanandroid.App
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.base.BaseVMActivity
import kim.bifrost.coldrain.wanandroid.databinding.ActivityInnerSystemBinding
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.ArticleData
import kim.bifrost.coldrain.wanandroid.repo.remote.bean.SystemData
import kim.bifrost.coldrain.wanandroid.view.adapter.InnerRvPagingAdapter
import kim.bifrost.coldrain.wanandroid.view.adapter.RVItemPagerAdapter
import kim.bifrost.coldrain.wanandroid.view.adapter.StandardPagerAdapter
import kim.bifrost.coldrain.wanandroid.view.viewmodel.InnerSystemViewModel
import kotlinx.coroutines.flow.Flow

class InnerSystemActivity : BaseVMActivity<InnerSystemViewModel, ActivityInnerSystemBinding>() {

    private val data by lazy { App.gson.fromJson(intent.getStringExtra("data"), SystemData::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        // Toolbar
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = data.name
        }
        binding.vpInnerSystem.adapter = RVItemPagerAdapter(this, lifecycleScope, data.children, object : RVItemPagerAdapter.CallBack {
            override val onCollect: BasePagingAdapter.Holder<HomeRvItemBinding>.(InnerRvPagingAdapter) -> Unit
                get() = viewModel.adapterCallback

            override suspend fun collectData(cid: Int): Flow<PagingData<ArticleData>> = viewModel.getSystemDataArticles(cid)
        })
        TabLayoutMediator(binding.tlInnerSystem, binding.vpInnerSystem) { tab, i ->
            tab.text = data.children[i].name
        }.attach()
    }

    override fun getViewBinding(): ActivityInnerSystemBinding = ActivityInnerSystemBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {
        fun startActivity(context: Context, data: SystemData) {
            context.startActivity(Intent(context, InnerSystemActivity::class.java).apply {
                putExtra("data", App.gson.toJson(data))
            })
        }
    }
}