package kim.bifrost.coldrain.wanandroid.view.viewmodel.frag

import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kim.bifrost.coldrain.wanandroid.R
import kim.bifrost.coldrain.wanandroid.base.BasePagingAdapter
import kim.bifrost.coldrain.wanandroid.databinding.HomeRvItemBinding
import kim.bifrost.coldrain.wanandroid.repo.data.FindPagingSource
import kim.bifrost.coldrain.wanandroid.repo.data.HomePagingSource
import kim.bifrost.coldrain.wanandroid.repo.data.UserData
import kim.bifrost.coldrain.wanandroid.repo.remote.ApiService
import kim.bifrost.coldrain.wanandroid.utils.getCollectFunc
import kim.bifrost.coldrain.wanandroid.utils.toast
import kim.bifrost.coldrain.wanandroid.utils.toastConcurrent
import kim.bifrost.coldrain.wanandroid.view.adapter.FindPagingDataAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.coldrain.wanandroid.view.viewmodel.frag.FindFragViewModel
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/18 19:54
 **/
class FindFragViewModel : ViewModel() {
    val collectCallback = getCollectFunc<FindPagingDataAdapter>(viewModelScope)

    val squareDataList = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            FindPagingSource()
        }
    ).flow
}